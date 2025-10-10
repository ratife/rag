package mg.tife.domain.repository;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;

public interface MessageRepository {
    public Response sendMessage(Message message);
    public Message saveMessage(Message message);
}
