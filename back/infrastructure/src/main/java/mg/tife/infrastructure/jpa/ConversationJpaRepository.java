package mg.tife.infrastructure.jpa;

import mg.tife.infrastructure.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationJpaRepository  extends JpaRepository<ConversationEntity, UUID> {
}