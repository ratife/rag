package mg.tife.usecase;

import mg.tife.domain.domain.Document;
import mg.tife.domain.repository.DocumentRepository;
import mg.tife.domain.usecase.document.AddDocumentUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddDocumentUsecaseTest {

    private MockDocumentRepository mockRepository;
    private AddDocumentUsecase usecase;

    @BeforeEach
    void init() {
        mockRepository = new MockDocumentRepository();
        usecase = new AddDocumentUsecase(mockRepository);
    }

    @Test
    void testExecute() {

        Document doc = new Document();
        // When
        usecase.execute(doc);

        // Then
        assertTrue(mockRepository.isIndexDocumentCalled(), "indexDocument should have been called");
        //assertArrayEquals(testContents, mockRepository.getIndexedContents(), "The contents provided to indexDocument should match");
        //assertEquals(testContentType, mockRepository.getIndexedContentType(), "The contentType provided to indexDocument should match");
    }

    /**
     * Mock implementation de DocumentRepository pour isoler le test
     */
    private static class MockDocumentRepository implements DocumentRepository {
        private boolean indexDocumentCalled = false;
        private Document document;

        @Override
        public void indexDocument(Document document) {
            this.indexDocumentCalled = true;
            this.document = document;
        }

        @Override
        public List<Document> listDocumentIndex() {
            return List.of();
        }

        public boolean isIndexDocumentCalled() {
            return indexDocumentCalled;
        }
    }
}
