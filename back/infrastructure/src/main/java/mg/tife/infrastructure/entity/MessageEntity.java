package mg.tife.infrastructure.entity;

import jakarta.persistence.*;
import mg.tife.domain.domain.Message;

import java.util.UUID;


@Entity
@Table(name = "messages")
public class MessageEntity {
    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String content;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id")
    private ResponseEntity response;

    @ManyToOne
    ConversationEntity  conversation;

    // Default constructor required by JPA
    public MessageEntity() {
    }

    public MessageEntity(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        if (message.getResponse() != null) {
            this.response = new ResponseEntity(message.getResponse());
        }
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

    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public ConversationEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationEntity conversation) {
        this.conversation = conversation;
    }


}