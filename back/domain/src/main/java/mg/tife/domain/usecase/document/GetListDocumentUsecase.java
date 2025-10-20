package mg.tife.domain.usecase.document;

import mg.tife.domain.domain.Document;
import mg.tife.domain.repository.DocumentRepository;
import java.util.List;

public class GetListDocumentUsecase {

    private DocumentRepository documentRepository;

    public GetListDocumentUsecase(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> execute() {
        return documentRepository.listDocumentIndex();
    }
}
