package pe.uni.poo_v_g7.bibliotecaapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CatalogacionAiRequest;
import pe.uni.poo_v_g7.bibliotecaapp.dto.CatalogacionAiResponse;

import static pe.uni.poo_v_g7.bibliotecaapp.util.ValidationUtils.*;

@Service
public class CatalogacionAiService {

    private final ChatClient chatClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CatalogacionAiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public CatalogacionAiResponse catalogar(CatalogacionAiRequest request) {

        String isbn = requireNotBlank(
                requireNonNull(
                        request.isbn(),
                        "El ISBN no puede ser nulo."
                ),
                "El ISBN no puede estar vacío."
        );

        String titulo = requireNotBlank(
                requireNonNull(
                        request.titulo(),
                        "El título no puede ser nulo."
                ),
                "El título no puede estar vacío."
        );

        String descripcion = requireNotBlank(
                requireNonNull(
                        request.descripcion(),
                        "La descripción/sinopsis no puede ser nula."
                ),
                "La descripción/sinopsis no puede estar vacía."
        );
        String prompt = """
                Eres un bibliotecario experto en catalogación.
                Analiza el libro y responde SOLO con JSON válido, sin markdown, sin texto adicional y sin bloques de código.

                Estructura exacta:
                {
                  "isbn": "string",
                  "titulo": "string",
                  "sinopsis": "string",
                  "categorias": [{"nombre": "string", "descripcion": "string"}],
                  "etiquetas": [{"nombre": "string"}]
                }

                Reglas:
                - Devuelve el ISBN exactamente igual al recibido.
                - Devuelve entre 1 y 3 categorías.
                - Devuelve entre 5 y 10 etiquetas temáticas.
                - La sinopsis debe ser breve, en español neutro, y no superar 80 palabras.
                - Las etiquetas deben ser concretas y útiles para búsqueda.
                - No inventes campos extra.

                Datos del libro:
                ISBN: %s
                Título: %s
                Descripción: %s
                """.formatted(isbn, titulo, descripcion);

        String raw = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        String json = extractJson(raw);

        try {
            return objectMapper.readValue(json, CatalogacionAiResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("La IA respondió un formato inválido.", e);
        }
    }

    private String extractJson(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalStateException("La IA devolvió una respuesta vacía.");
        }

        int start = raw.indexOf('{');
        int end = raw.lastIndexOf('}');

        if (start >= 0 && end > start) {
            return raw.substring(start, end + 1).trim();
        }

        return raw.trim();
    }
}