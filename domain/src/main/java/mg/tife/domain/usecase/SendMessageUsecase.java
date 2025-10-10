package mg.tife.domain.usecase;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.domain.repository.MessageRepository;

public class SendMessageUsecase {

    private MessageRepository messageRepository;

    public SendMessageUsecase(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Response execute(String messageContent) {
        Message message = new Message(messageContent);
        Response response = messageRepository.sendMessage(message);
        message.setResponse(response);
        //messageRepository.saveMessage(message);
        return response;
    }
}
