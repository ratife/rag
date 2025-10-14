package mg.tife.domain.domain;

import java.util.UUID;

public class Response {
    private UUID id;
    private String content;

    public Response(String content) {
        this.id = UUID.randomUUID();
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
