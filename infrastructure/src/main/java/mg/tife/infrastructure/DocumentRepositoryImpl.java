package mg.tife.infrastructure;

import mg.tife.domain.repository.DocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    ElasticsearchVectorStore vectorStore;

    DocumentRepositoryImpl(ElasticsearchVectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void indexDocument(byte[] contents, String contentType) {

        System.out.println("📂 Indexing document with  vectorStore.getName(): " + vectorStore.getName());

        try { // PDF
            Resource resource = new ByteArrayResource(contents) {
                @Override
                public String getFilename() {
                    return "uploaded-file.pdf"; // Nom fictif, nécessaire pour certains readers
                }
            };
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);

            // 2. Extraire le texte sous forme de Document Spring AI
            List<Document> documents = pdfReader.read();

            if (documents == null || documents.isEmpty()) {
                System.out.println("⚠️ Aucun texte trouvé dans le document.");
                return;
            }

            System.out.println("✅ Texte extrait. Nombre de pages : " + documents.size());

            // 3. Fractionner en petits chunks
            TokenTextSplitter splitter = new TokenTextSplitter();


            List<Document> splitDocuments = splitter.apply(documents);

            System.out.println("✂️ Document fractionné en " + splitDocuments.size() + " morceaux.");

            // 4. Indexer dans pgvector via VectorStore
            vectorStore.add(splitDocuments);

            System.out.println("✅ Document indexé avec succès dans pgvector.");

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'indexation du document : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
