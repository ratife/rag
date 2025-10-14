package mg.tife.domain.domain;

import java.util.UUID;

public class Message {
    private UUID id;
    private String content;
    private Response response;
    private Conversation conversation;

    public Message(String content, Conversation conversation) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.conversation = conversation;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Response getResponse() {
        return response;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
