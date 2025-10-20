package mg.tife.infrastructure.repository;

import mg.tife.domain.domain.Conversation;
import mg.tife.domain.repository.ConversationRepository;
import mg.tife.infrastructure.entity.ConversationEntity;
import mg.tife.infrastructure.jpa.ConversationJpaRepository;
import mg.tife.infrastructure.mapper.ConversationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConversationRepositoryImpl implements ConversationRepository {

    private ConversationJpaRepository conversationJpaRepository;

    public ConversationRepositoryImpl(ConversationJpaRepository conversationJpaRepository) {
        this.conversationJpaRepository = conversationJpaRepository;
    }
    @Override
    public Conversation createConversation(String title) {
        ConversationEntity conv = new ConversationEntity(title);
        ConversationEntity entity = conversationJpaRepository.save(conv);
        return ConversationMapper.INSTANCE.entityToObj(entity);
    }

    @Override
    public List<Conversation> getAllConversation() {
        List<ConversationEntity> entities =  conversationJpaRepository.findAll();
        return entities.stream().map(ConversationMapper.INSTANCE::entityToObj).toList();
    }

    @Override
    public Optional<Conversation> getConversationById(UUID converssationID) {
        Optional<ConversationEntity> converation =  conversationJpaRepository.findById(converssationID);
        return converation.map(ConversationMapper.INSTANCE::entityToObj);
    }
}
