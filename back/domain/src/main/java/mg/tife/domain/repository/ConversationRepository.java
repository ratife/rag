package mg.tife.domain.repository;

import mg.tife.domain.domain.Conversation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository {
    Conversation createConversation(String title);

    List<Conversation> getAllConversation();

    Optional<Conversation> getConversationById(UUID converssationID);
}
