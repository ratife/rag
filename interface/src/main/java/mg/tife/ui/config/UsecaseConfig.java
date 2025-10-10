package mg.tife.ui.config;

import mg.tife.domain.repository.DocumentRepository;
import mg.tife.domain.repository.MessageRepository;
import mg.tife.domain.usecase.AddDocumentUsecase;
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
    public SendMessageUsecase sendMessageUsecase(MessageRepository messageRepository){
        return new SendMessageUsecase(messageRepository);
    }
}
