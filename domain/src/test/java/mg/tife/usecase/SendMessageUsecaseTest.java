package mg.tife.usecase;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.exception.ElementNotFundException;
import mg.tife.domain.repository.ConversationRepository;
import mg.tife.domain.repository.MessageRepository;
import mg.tife.domain.usecase.message.SendMessageUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SendMessageUsecaseTest {

    private static final String TEST_MESSAGE_CONTENT = "Test message";
    private static final String TEST_RESPONSE_CONTENT = "Test response";

    private MockMessageRepository mockMessageRepository;
    private MockConversationRepository mockConvRepository;
    private SendMessageUsecase usecase;

    @BeforeEach
    void init() {
        mockMessageRepository = new MockMessageRepository();
        mockConvRepository = new MockConversationRepository();
        usecase = new SendMessageUsecase(mockMessageRepository,mockConvRepository);
    }

    @Test
    void testExecute() throws ElementNotFundException {
        // When: exécution du use case
        UUID indexName = UUID.randomUUID();
        Response response = usecase.execute(indexName,TEST_MESSAGE_CONTENT,UUID.randomUUID());

        // Then: vérifier la réponse
        assertNotNull(response, "La réponse ne doit pas être null");
        assertEquals(TEST_RESPONSE_CONTENT, response.getContent(), "Le contenu de la réponse doit correspondre");

        // Vérifier que sendMessage a été appelé avec un message correct
        assertNotNull(mockMessageRepository.getSentMessage(), "Le message envoyé ne doit pas être null");
        assertEquals(TEST_MESSAGE_CONTENT, mockMessageRepository.getSentMessage().getContent(),
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
        public Response sendMessage(UUID indexName,Message message) {
            this.sentMessage = message;
            return new Response(TEST_RESPONSE_CONTENT);
        }

        @Override
        public Message saveMessage(Message message) {
            this.savedMessage = message;
            return message;
        }

        @Override
        public List<Message> findByConversationId(UUID converssationID) {
            return List.of();
        }

        public Message getSentMessage() {
            return sentMessage;
        }

    }


    private static class MockConversationRepository implements ConversationRepository {

        @Override
        public Conversation createConversation(String title) {
            return new Conversation();
        }

        @Override
        public List<Conversation> getAllConversation() {
            return List.of(new Conversation());
        }

        @Override
        public Optional<Conversation> getConversationById(UUID converssationID) {
            Conversation conv = new Conversation();
            conv.setId(converssationID);
            return Optional.of(conv);
        }
    }

}
