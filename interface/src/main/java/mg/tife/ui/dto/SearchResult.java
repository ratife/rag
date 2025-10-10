package mg.tife.ui.dto;


/**
 * Represents a search result from the vector store.
 */
public class SearchResult {
    private Document document;
    private double score;

    /**
     * Create a new search result.
     *
     * @param document The document
     * @param score The similarity score
     */
    public SearchResult(Document document, double score) {
        this.document = document;
        this.score = score;
    }

    /**
     * Get the document.
     *
     * @return The document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Get the similarity score.
     *
     * @return The similarity score
     */
    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "documentId='" + document.getId() + '\'' +
                ", score=" + score +
                '}';
    }
}