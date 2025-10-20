package mg.tife.infrastructure.jpa;

import mg.tife.infrastructure.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageJpaRepository extends JpaRepository<MessageEntity, String> {
    List<MessageEntity> findByConversationId(UUID converssationID);
}