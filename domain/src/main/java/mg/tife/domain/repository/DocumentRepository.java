package mg.tife.domain.repository;

public interface DocumentRepository {
    public void indexDocument(byte[] contents,String contentType);
}
