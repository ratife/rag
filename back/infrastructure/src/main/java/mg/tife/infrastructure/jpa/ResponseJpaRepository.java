package mg.tife.infrastructure.jpa;

import mg.tife.infrastructure.entity.ResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseJpaRepository extends JpaRepository<ResponseEntity, String> {
}