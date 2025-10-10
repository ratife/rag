package mg.tife.ui.dto;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a document to be stored in the vector store.
 */
public class Document {
    private String id;
    private String content;
    private Map<String, Object> metadata;

    /**
     * Create a new document.
     *
     * @param id The document ID
     * @param content The document content
     */
    public Document(String id, String content) {
        this.id = id;
        this.content = content;
        this.metadata = new HashMap<>();
    }

    /**
     * Create a new document with metadata.
     *
     * @param id The document ID
     * @param content The document content
     * @param metadata The document metadata
     */
    public Document(String id, String content, Map<String, Object> metadata) {
        this.id = id;
        this.content = content;
        this.metadata = metadata != null ? metadata : new HashMap<>();
    }

    /**
     * Get the document ID.
     *
     * @return The document ID
     */
    public String getId() {
        return id;
    }

    /**
     * Get the document content.
     *
     * @return The document content
     */
    public String getContent() {
        return content;
    }

    /**
     * Get the document metadata.
     *
     * @return The document metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * Add metadata to the document.
     *
     * @param key The metadata key
     * @param value The metadata value
     */
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }
}