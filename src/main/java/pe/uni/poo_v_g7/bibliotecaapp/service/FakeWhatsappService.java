package pe.uni.poo_v_g7.bibliotecaapp.service;

import org.springframework.stereotype.Service;

@Service
public class FakeWhatsappService implements WhatsappService {

    @Override
    public void sendMessage(
            String telefono,
            String mensaje
    ) {

        System.out.println(
                "[WHATSAPP] "
                        + telefono
                        + " -> "
                        + mensaje
        );
    }
}
