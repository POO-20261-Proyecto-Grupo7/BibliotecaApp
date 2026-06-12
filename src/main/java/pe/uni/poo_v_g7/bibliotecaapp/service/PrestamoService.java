package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.PrestamoDto;
import pe.uni.poo_v_g7.bibliotecaapp.dto.RegistrarPrestamoPorEjemplarCodigoRequest;
import pe.uni.poo_v_g7.bibliotecaapp.entity.*;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.ClienteMapper;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.EjemplarMapper;
import pe.uni.poo_v_g7.bibliotecaapp.mapper.PrestamoMapper;
import pe.uni.poo_v_g7.bibliotecaapp.repository.ClienteRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.EjemplarRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.LibroRepository;
import pe.uni.poo_v_g7.bibliotecaapp.repository.PrestamoRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PrestamoService {

    private final Clock clock;

    private final PrestamoRepository prestamoRepository;

    private final PrestamoMapper prestamoMapper;

    private final ClienteService clienteService;

    private final EjemplarService ejemplarService;

    private final LibroRepository libroRepository;

    public PrestamoService(
            Clock clock,
            PrestamoRepository prestamoRepository,
            PrestamoMapper prestamoMapper,
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper,
            EjemplarRepository ejemplarRepository,
            EjemplarMapper ejemplarMapper,
            LibroRepository libroRepository
    ) {
        this.clock = clock;
        this.prestamoRepository = prestamoRepository;
        this.prestamoMapper = prestamoMapper;
        this.clienteService = new ClienteService(clienteRepository, clienteMapper);
        this.ejemplarService = new EjemplarService(ejemplarRepository, ejemplarMapper);
        this.libroRepository = libroRepository;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public PrestamoDto registerPrestamo(RegistrarPrestamoPorEjemplarCodigoRequest request) {
        Integer idCliente = request.getIdCliente();
        if (idCliente == null) {
            throw new IllegalArgumentException("El ID de cliente no puede ser nulo.");
        }
        var cliente = clienteService.getClienteEntity(request.getIdCliente());
        if (!cliente.getHabilitado()) {
            throw new IllegalArgumentException("El cliente con ID '" + idCliente + "' no está habilitado para realizar préstamos.");
        }
        var requestEjemplares = request.getEjemplares();
        var ejemplares = ejemplarService.findAllEjemplarEntityForUpdate(requestEjemplares);
        if (ejemplares.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron ejemplares.");
        }
        Set<String> faltantes = new HashSet<>(requestEjemplares);
        for (var ejemplar : ejemplares) {
            if (ejemplar.getEstado() != EstadoEjemplar.DISPONIBLE) {
                throw new IllegalArgumentException("El ejemplar con código '" + ejemplar.getCodigo() + "' no está disponible.");
            }
            faltantes.remove(ejemplar.getCodigo());
        }

        if (!faltantes.isEmpty()) {
            throw new IllegalArgumentException("Los siguientes códigos no corresponden a ejemplares existentes: " + String.join(", ", faltantes));
        }

        var fechaPrestamo = LocalDateTime.now(clock);
        LocalDateTime fechaLimite = fechaPrestamo.plusDays(14);

        Prestamo prestamo = new Prestamo();
        prestamo.setCliente(cliente);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaLimite(fechaLimite);
        prestamo.setEstado(EstadoPrestamo.ACTIVO);

        for (var ejemplar : ejemplares) {
            prestamo.getEjemplares().add(ejemplar);
            ejemplar.setEstado(EstadoEjemplar.PRESTADO);
        }

        descontarStockPorLibro(ejemplares);

        Prestamo saved = prestamoRepository.save(prestamo);

        return prestamoMapper.toDetailedDto(saved);
    }

    private void descontarStockPorLibro(List<Ejemplar> ejemplares) {
        Map<Integer, Long> cantidadPorLibro = ejemplares.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getLibro().getIdLibro(),
                        Collectors.counting()
                ));

        List<Libro> libros = libroRepository.findAllDetailedForUpdateByIdLibroIn(cantidadPorLibro.keySet());

        if (libros.size() != cantidadPorLibro.size()) {
            Set<Integer> encontrados = libros.stream()
                    .map(Libro::getIdLibro)
                    .collect(Collectors.toSet());

            Set<Integer> faltantes = new LinkedHashSet<>(cantidadPorLibro.keySet());
            faltantes.removeAll(encontrados);

            throw new IllegalStateException("No existen libros para descontar stock: " + faltantes);
        }

        for (Libro libro : libros) {
            long cantidad = cantidadPorLibro.getOrDefault(libro.getIdLibro(), 0L);
            int stockActual = libro.getStock() == null ? 0 : libro.getStock();

            if (stockActual < cantidad) {
                throw new IllegalStateException(
                        "Stock insuficiente para el libro id " + libro.getIdLibro()
                );
            }

            libro.setStock((int) (stockActual - cantidad));
        }
    }
}
