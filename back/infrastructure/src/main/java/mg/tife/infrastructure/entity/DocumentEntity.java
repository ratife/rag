package mg.tife.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "documents")
public class DocumentEntity {
    @Id
    private UUID indexName;
    private String contentType;
    private String fileName;
    private String description;

    public UUID getIndexName() {
        return indexName;
    }

    public void setIndexName(UUID indexName) {
        this.indexName = indexName;
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
