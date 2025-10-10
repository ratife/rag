package mg.tife.infrastructure;

import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;
import mg.tife.infrastructure.entity.MessageEntity;
import mg.tife.infrastructure.mapper.MessageMapper;
import mg.tife.infrastructure.repository.MessageJpaRepository;
import mg.tife.domain.repository.MessageRepository;
import mg.tife.infrastructure.repository.ResponseJpaRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepositoryImpl  implements MessageRepository {

    private ChatClient chatClient;
    private MessageJpaRepository messageJpaRepository;
    private ResponseJpaRepository responseJpaRepository;

    private MessageMapper messageMapper;


    MessageRepositoryImpl(ElasticsearchVectorStore vectorStore,
                          ChatModel chatModel,
                          MessageMapper messageMapper,
                          MessageJpaRepository messageJpaRepository,
                          ResponseJpaRepository responseJpaRepository) {

        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore),
                        MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();

        this.messageMapper = messageMapper;
        this.messageJpaRepository = messageJpaRepository;
        this.responseJpaRepository = responseJpaRepository;
    }

    @Override
    public Response sendMessage(Message message) {

        ChatResponse responseTXT = this.chatClient.prompt()
                .user(message.getContent())
                .call()
                .chatResponse();

        Response response = new Response(responseTXT.getResult().getOutput().getText());

        // Save the message with its response
        //message.setResponse(response);
        //saveMessage(message);

        return response;
    }

    @Override
    public Message saveMessage(Message message) {
        MessageEntity messageEntity = messageMapper.messageToEntity(message);
        messageEntity = messageJpaRepository.save(messageEntity);
        return messageMapper.entityToMessage(messageEntity);
    }

}
