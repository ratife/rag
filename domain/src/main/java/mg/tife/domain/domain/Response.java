package mg.tife.domain.domain;

import java.util.UUID;

public class Response {
    private UUID id;
    private String content;

    public Response(String content) {
        this.id = UUID.randomUUID();
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
