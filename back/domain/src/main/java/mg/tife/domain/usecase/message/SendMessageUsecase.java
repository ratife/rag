package mg.tife.domain.usecase.message;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.exception.ElementNotFundException;
import mg.tife.domain.repository.ConversationRepository;
import mg.tife.domain.repository.MessageRepository;

import java.util.Optional;
import java.util.UUID;

public class SendMessageUsecase {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    public SendMessageUsecase(MessageRepository messageRepository,ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    public Response execute(UUID indexName,String messageContent, UUID converssationID) throws ElementNotFundException {
        Optional<Conversation> conversationOptional;
        if(converssationID==null){
            conversationOptional = Optional.of(conversationRepository.createConversation("New Conversation"));
        }
        else{
            conversationOptional = conversationRepository.getConversationById(converssationID);
        }
        if (conversationOptional.isEmpty()) {
            System.out.println("convID: " + converssationID + " not found");
            throw new ElementNotFundException("Conversation ID = " + converssationID.toString() + " not found");
        }
        Message message = new Message(messageContent,conversationOptional.get());
        Response response = messageRepository.sendMessage(indexName,message);
        message.setResponse(response);
        messageRepository.saveMessage(message);
        return response;
    }
}
