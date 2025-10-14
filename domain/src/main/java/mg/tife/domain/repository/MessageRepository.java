package mg.tife.domain.repository;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    public Response sendMessage(Message message);
    public Message saveMessage(Message message);

    List<Message> findByConversationId(UUID converssationID);
}
