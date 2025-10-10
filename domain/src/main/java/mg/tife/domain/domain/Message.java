package mg.tife.domain.domain;

import java.util.UUID;

public class Message {
    private UUID id;
    private String content;
    private Response response;

    public Message(String content) {
        this.id = UUID.randomUUID();
        this.content = content;
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
}
