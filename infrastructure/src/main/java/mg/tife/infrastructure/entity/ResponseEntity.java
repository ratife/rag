package mg.tife.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import mg.tife.domain.domain.Response;

import java.util.UUID;

@Entity
@Table(name = "responses")
public class ResponseEntity {
    @Id
    private String id;
    private String content;

    // Default constructor required by JPA
    public ResponseEntity() {
    }

    public ResponseEntity(Response response) {
        this.id = UUID.randomUUID().toString();
        this.content = response.getContent();
    }

    public Response toDomain() {
        Response response = new Response(this.content);
        return response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
