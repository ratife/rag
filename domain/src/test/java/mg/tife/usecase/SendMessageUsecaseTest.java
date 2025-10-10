package mg.tife.usecase;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.repository.MessageRepository;
import mg.tife.domain.usecase.SendMessageUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SendMessageUsecaseTest {

    private static final String TEST_MESSAGE_CONTENT = "Test message";
    private static final String TEST_RESPONSE_CONTENT = "Test response";

    private MockMessageRepository mockRepository;
    private SendMessageUsecase usecase;

    @BeforeEach
    void init() {
        mockRepository = new MockMessageRepository();
        usecase = new SendMessageUsecase(mockRepository);
    }

    @Test
    void testExecute() {
        // When: exécution du use case
        Response response = usecase.execute(TEST_MESSAGE_CONTENT);

        // Then: vérifier la réponse
        assertNotNull(response, "La réponse ne doit pas être null");
        assertEquals(TEST_RESPONSE_CONTENT, response.getContent(), "Le contenu de la réponse doit correspondre");

        // Vérifier que sendMessage a été appelé avec un message correct
        assertNotNull(mockRepository.getSentMessage(), "Le message envoyé ne doit pas être null");
        assertEquals(TEST_MESSAGE_CONTENT, mockRepository.getSentMessage().getContent(),
                "Le contenu du message envoyé doit correspondre");

        // Vérifier que saveMessage a été appelé avec un message qui contient la réponse
        //assertNotNull(mockRepository.getSavedMessage(), "Le message sauvegardé ne doit pas être null");
        //assertNotNull(mockRepository.getSavedMessage().getResponse(), "Le message sauvegardé doit contenir une réponse");
        //assertEquals(TEST_RESPONSE_CONTENT, mockRepository.getSavedMessage().getResponse().getContent(),
        //        "Le contenu de la réponse du message sauvegardé doit correspondre");
    }

    /**
     * Implémentation mock de MessageRepository pour isoler le test
     */
    private static class MockMessageRepository implements MessageRepository {
        private Message sentMessage;
        private Message savedMessage;

        @Override
        public Response sendMessage(Message message) {
            this.sentMessage = message;
            return new Response(TEST_RESPONSE_CONTENT);
        }

        @Override
        public Message saveMessage(Message message) {
            this.savedMessage = message;
            return message;
        }

        public Message getSentMessage() {
            return sentMessage;
        }

        public Message getSavedMessage() {
            return savedMessage;
        }
    }
}
