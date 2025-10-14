package mg.tife.infrastructure.repository;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.infrastructure.entity.MessageEntity;
import mg.tife.infrastructure.jpa.MessageJpaRepository;
import mg.tife.infrastructure.mapper.MessageMapper;
import mg.tife.domain.repository.MessageRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageRepositoryImpl  implements MessageRepository {

    private ChatClient chatClient;
    private MessageJpaRepository messageJpaRepository;

    MessageRepositoryImpl(ElasticsearchVectorStore vectorStore,
                          ChatModel chatModel,
                          MessageJpaRepository messageJpaRepository) {

        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        //this.messageMapper = messageMapper;
        this.messageJpaRepository = messageJpaRepository;
    }

    @Override
    public Response sendMessage(Message message) {
        ChatResponse responseTXT = this.chatClient.prompt()
                .user(message.getContent())
                .call()
                .chatResponse();
        Response response = new Response(responseTXT.getResult().getOutput().getText());
        return response;
    }

    @Override
    public Message saveMessage(Message message) {
        System.out.println("Message convID = " + message.getConversation().getId());
        MessageEntity messageEntity = MessageMapper.INSTANCE.messageToEntity(message);
        messageEntity = messageJpaRepository.save(messageEntity);
        return MessageMapper.INSTANCE.entityToMessage(messageEntity);
    }

    @Override
    public List<Message> findByConversationId(UUID converssationID) {
        List<MessageEntity>  messages = messageJpaRepository.findByConversationId(converssationID);
        return messages.stream().map(MessageMapper.INSTANCE::entityToMessage).toList();
    }

}
