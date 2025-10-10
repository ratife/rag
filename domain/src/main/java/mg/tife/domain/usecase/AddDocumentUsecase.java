package mg.tife.domain.usecase;

import mg.tife.domain.repository.DocumentRepository;

public class AddDocumentUsecase {

    private DocumentRepository documentRepository;

    public AddDocumentUsecase(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void execute(byte[] contents, String contentType) {
        documentRepository.indexDocument(contents,contentType);
    }
}
