package pe.uni.poo_v_g7.bibliotecaapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    public void send(
            String destino,
            String asunto,
            String cuerpo
    ) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(destino);
        message.setSubject(asunto);
        message.setText(cuerpo);

        sender.send(message);
    }
}