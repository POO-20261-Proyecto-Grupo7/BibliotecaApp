package pe.uni.poo_v_g7.bibliotecaapp.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pe.uni.poo_v_g7.bibliotecaapp.entity.Prestamo;
import pe.uni.poo_v_g7.bibliotecaapp.repository.PrestamoRepository;
import pe.uni.poo_v_g7.bibliotecaapp.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class PrestamoNotificationScheduler {

    private final PrestamoRepository prestamoRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 * * * *")
    public void enviarAlertas() {

        LocalDateTime ahora = LocalDateTime.now();

        LocalDateTime inicio = ahora.plusHours(47);
        LocalDateTime fin = ahora.plusHours(49);

        List<Prestamo> prestamos =
                prestamoRepository.findPrestamosPorVencer(
                        inicio,
                        fin
                );

        for (Prestamo prestamo : prestamos) {
            notificationService.enviarNotificacion(prestamo);
        }
    }
}
