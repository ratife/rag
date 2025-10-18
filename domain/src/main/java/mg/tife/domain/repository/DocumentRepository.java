package mg.tife.domain.repository;

import mg.tife.domain.domain.Document;

import java.util.List;

public interface DocumentRepository {
    public void indexDocument(Document document);
    public List<Document> listDocumentIndex();
}