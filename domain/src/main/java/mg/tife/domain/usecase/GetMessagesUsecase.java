package mg.tife.domain.usecase;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.exception.ElementNotFundException;
import mg.tife.domain.repository.ConversationRepository;
import mg.tife.domain.repository.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetMessagesUsecase {

    private MessageRepository messageRepository;
    private ConversationRepository conversationRepository;

    public GetMessagesUsecase(MessageRepository messageRepository,
                              ConversationRepository conversationRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    public List<Message> execute(UUID converssationID) throws ElementNotFundException {
        Optional<Conversation> conversationOptional = conversationRepository.getConversationById(converssationID);
        if (conversationOptional.isEmpty()) {
            throw new ElementNotFundException("Conversation ID = " + converssationID.toString() + " not found");
        }
        return  messageRepository.findByConversationId(converssationID);
    }
}
