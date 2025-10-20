package mg.tife.domain.usecase.conversation;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.repository.ConversationRepository;

import java.util.UUID;

public class CreateConversationUsecase {
    private ConversationRepository conversationRepository;

    public CreateConversationUsecase(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public Conversation execute(String title) {
       return conversationRepository.createConversation(title);
    }
}
