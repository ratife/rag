package mg.tife.ui.config;

import mg.tife.domain.repository.ConversationRepository;
import mg.tife.domain.repository.DocumentRepository;
import mg.tife.domain.repository.MessageRepository;
import mg.tife.domain.usecase.AddDocumentUsecase;
import mg.tife.domain.usecase.GetMessagesUsecase;
import mg.tife.domain.usecase.conversation.CreateConversationUsecase;
import mg.tife.domain.usecase.conversation.GetListConversationUsecase;
import mg.tife.domain.usecase.SendMessageUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsecaseConfig {

    @Bean
    public AddDocumentUsecase addDocumentUsecase(DocumentRepository documentRepository){
        return new AddDocumentUsecase(documentRepository);
    }

    @Bean
    public SendMessageUsecase sendMessageUsecase(MessageRepository messageRepository, ConversationRepository conversationRepository){
        return new SendMessageUsecase(messageRepository,conversationRepository);
    }

    @Bean
    public CreateConversationUsecase createConversationUsecase(ConversationRepository conversationRepository){
        return new CreateConversationUsecase(conversationRepository);
    }

    @Bean
    public GetListConversationUsecase getListConversationUsecase(ConversationRepository conversationRepository){
        return new GetListConversationUsecase(conversationRepository);
    }

    @Bean
    public GetMessagesUsecase getMessagesUsecase(MessageRepository messageRepository,ConversationRepository conversationRepository){
        return new GetMessagesUsecase(messageRepository,conversationRepository);
    }
}
