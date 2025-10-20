package mg.tife.domain.usecase.conversation;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.repository.ConversationRepository;

import java.util.List;

public class GetListConversationUsecase {
    private ConversationRepository conversationRepository;

    public GetListConversationUsecase(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public List<Conversation>  execute() {
        return conversationRepository.getAllConversation();
    }
}
