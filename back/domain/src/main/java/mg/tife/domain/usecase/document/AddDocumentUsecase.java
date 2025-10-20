package mg.tife.domain.usecase.document;

import mg.tife.domain.domain.Document;
import mg.tife.domain.repository.DocumentRepository;

public class AddDocumentUsecase {

    private DocumentRepository documentRepository;

    public AddDocumentUsecase(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void execute(Document document) {
        documentRepository.indexDocument(document);
    }
}
