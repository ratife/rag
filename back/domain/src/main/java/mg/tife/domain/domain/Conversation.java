package mg.tife.domain.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private UUID id;
    private String title;
    private List<Message> messages;

    public Conversation() {
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

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
