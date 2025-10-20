package mg.tife.infrastructure.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class ConversationEntity {
    @Id
    private UUID id;
    private String title;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageEntity> messages;


    public ConversationEntity() {
        this.messages = new ArrayList<>();
    }

    public ConversationEntity(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.messages = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void addMessage(MessageEntity message) {
        this.messages.add(message);
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
