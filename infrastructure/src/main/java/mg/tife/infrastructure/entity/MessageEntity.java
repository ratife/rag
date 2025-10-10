package mg.tife.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import mg.tife.domain.domain.Message;
import mg.tife.domain.domain.Response;

@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    private String id;
    private String content;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    private ResponseEntity response;

    // Default constructor required by JPA
    public MessageEntity() {
    }

    public MessageEntity(Message message) {
        this.id = message.getId().toString();
        this.content = message.getContent();
        if (message.getResponse() != null) {
            this.response = new ResponseEntity(message.getResponse());
        }
    }

    public Message toDomain() {
        Message message = new Message(this.content);
        if (this.response != null) {
            Response domainResponse = this.response.toDomain();
            message.setResponse(domainResponse);
        }
        return message;
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

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }
}