package pe.uni.poo_v_g7.bibliotecaapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo;
import pe.uni.poo_v_g7.bibliotecaapp.entity.RenovacionPrestamo;
import pe.uni.poo_v_g7.bibliotecaapp.repository.RenovacionPrestamoRepository;

import java.util.UUID;

//@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RenovacionPrestamoRepository renovacionRepository;

    private final EmailService emailService;

    private final WhatsappService whatsappService;

    public void enviarNotificacion(
            Prestamo prestamo
    ) {

        String token = UUID.randomUUID().toString();

        RenovacionPrestamo renovacion = new RenovacionPrestamo();

        renovacion.setPrestamo(prestamo);
        renovacion.setToken(token);
        renovacion.setUtilizado(false);

        renovacionRepository.save(renovacion);

        String enlace = "https://biblioteca.pe/api/prestamos/renovar?token=" + token;

        enviarCorreo(prestamo, enlace);
        enviarWhatsapp(prestamo, enlace);
    }

    private void enviarWhatsapp(Prestamo prestamo, String enlace) {
    }

    private void enviarCorreo(Prestamo prestamo, String enlace) {
    }
}
