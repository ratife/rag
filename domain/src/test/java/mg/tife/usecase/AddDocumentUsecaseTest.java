package mg.tife.usecase;

import mg.tife.domain.repository.DocumentRepository;
import mg.tife.domain.usecase.AddDocumentUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
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
        // Given
        byte[] testContents = "test content".getBytes(StandardCharsets.UTF_8);
        String testContentType = "text/plain";

        // When
        usecase.execute(testContents, testContentType);

        // Then
        assertTrue(mockRepository.isIndexDocumentCalled(), "indexDocument should have been called");
        assertArrayEquals(testContents, mockRepository.getIndexedContents(), "The contents provided to indexDocument should match");
        assertEquals(testContentType, mockRepository.getIndexedContentType(), "The contentType provided to indexDocument should match");
    }

    /**
     * Mock implementation de DocumentRepository pour isoler le test
     */
    private static class MockDocumentRepository implements DocumentRepository {
        private boolean indexDocumentCalled = false;
        private byte[] indexedContents;
        private String indexedContentType;

        @Override
        public void indexDocument(byte[] contents, String contentType) {
            this.indexDocumentCalled = true;
            this.indexedContents = contents;
            this.indexedContentType = contentType;
        }

        public boolean isIndexDocumentCalled() {
            return indexDocumentCalled;
        }

        public byte[] getIndexedContents() {
            return indexedContents;
        }

        public String getIndexedContentType() {
            return indexedContentType;
        }
    }
}
