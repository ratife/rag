package mg.tife.domain.domain;

import java.util.UUID;

public class Document {

    private UUID indexName;
    private byte[] contents;
    private String contentType;
    private String fileName;
    private String description;

    public UUID getIndexName() {
        return indexName;
    }

    public void setIndexName(UUID indexName) {
        this.indexName = indexName;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
