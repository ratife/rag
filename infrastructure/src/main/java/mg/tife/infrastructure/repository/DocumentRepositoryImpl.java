package mg.tife.infrastructure.repository;

import mg.tife.domain.domain.Document;
import mg.tife.domain.repository.DocumentRepository;
import mg.tife.infrastructure.entity.DocumentEntity;
import mg.tife.infrastructure.jpa.DocumentJpaRepository;
import mg.tife.infrastructure.mapper.DocumentMapper;
import mg.tife.infrastructure.vector.spec.VectorStoreManager;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentRepositoryImpl implements DocumentRepository {

    private VectorStoreManager vectorStoreManager;
    private DocumentJpaRepository documentJpaRepository;
    private RestClient client;

    DocumentRepositoryImpl(VectorStoreManager vectorStoreManager,
                           DocumentJpaRepository documentJpaRepository,
                           RestClient client) {
        this.vectorStoreManager = vectorStoreManager;
        this.documentJpaRepository = documentJpaRepository;
        this.client = client;
    }



    @Override
    public void indexDocument(Document document) {
        VectorStore vectorStore = vectorStoreManager.getVectorStore(document.getIndexName().toString());
        System.out.println("📂 Indexing document with  vectorStore.getName(): " + vectorStore.getName());
        try { // PDF
            Resource resource = new ByteArrayResource(document.getContents()) {
                @Override
                public String getFilename() {
                    return document.getFileName(); // Nom fictif, nécessaire pour certains readers
                }
            };
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);
            // 2. Extraire le texte sous forme de Document Spring AI
            List<org.springframework.ai.document.Document> documents = pdfReader.read();

            if (documents == null || documents.isEmpty()) {
                System.out.println("⚠️ Aucun texte trouvé dans le document.");
                return;
            }
            System.out.println("✅ Texte extrait. Nombre de pages : " + documents.size());
            // 3. Fractionner en petits chunks
            TokenTextSplitter splitter = new TokenTextSplitter();

            List<org.springframework.ai.document.Document> splitDocuments = splitter.apply(documents);

            System.out.println("✂️ Document fractionné en " + splitDocuments.size() + " morceaux.");

            // 4. Indexer dans pgvector via VectorStore
            vectorStore.add(splitDocuments);
            DocumentEntity entity = DocumentMapper.INSTANCE.domainToEntity(document);
            documentJpaRepository.save(entity);

            System.out.println("✅ Document indexé avec succès dans pgvector.");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'indexation du document : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<mg.tife.domain.domain.Document> listDocumentIndex() {
        return documentJpaRepository.findAll().stream().map(DocumentMapper.INSTANCE::entityToDomain).toList();
    }
}
