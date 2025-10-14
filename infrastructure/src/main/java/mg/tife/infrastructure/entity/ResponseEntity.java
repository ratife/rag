package mg.tife.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import mg.tife.domain.domain.Response;

import java.util.UUID;

@Entity
@Table(name = "responses")
public class ResponseEntity {
    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String content;

    // Default constructor required by JPA
    public ResponseEntity() {
    }

    public ResponseEntity(Response response) {
        this.content = response.getContent();
    }

    public Response toDomain() {
        Response response = new Response(this.content);
        return response;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
