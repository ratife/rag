package mg.tife.infrastructure.vector.impl;

import jakarta.annotation.PostConstruct;
import mg.tife.infrastructure.entity.DocumentEntity;
import mg.tife.infrastructure.jpa.DocumentJpaRepository;
import mg.tife.infrastructure.vector.spec.VectorStoreManager;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.TokenCountBatchingStrategy;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStoreOptions;
import org.springframework.ai.vectorstore.elasticsearch.SimilarityFunction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VectorStoreManagerImpl implements VectorStoreManager {

    private final Map<String, VectorStore> vectorStores = new HashMap<>();
    private final RestClient restClient;
    private final EmbeddingModel embeddingModel;
    private final DocumentJpaRepository documentJpaRepository;

    private static final int DIMENSIONS = 1024;

    VectorStoreManagerImpl(RestClient restClient, EmbeddingModel embeddingModel, DocumentJpaRepository documentJpaRepository){
        this.restClient = restClient;
        this.embeddingModel = embeddingModel;
        this.documentJpaRepository = documentJpaRepository;

    }

    @PostConstruct
    public void init() {
        List<DocumentEntity> listDoc = this.documentJpaRepository.findAll();
        System.out.println("************ NBR-DOC  :"+ listDoc.size());
        listDoc.forEach(e->{
            System.out.println("getIndexName : "+ e.getFileName() + " , " + e.getIndexName().toString());
            VectorStore vectorStore = loadVectorStore(e.getIndexName().toString());
            vectorStores.put(e.getIndexName().toString(), vectorStore);
        });
    }

    public VectorStore getVectorStore(String indexName) {
        if(!vectorStores.containsKey(indexName)){
            createStore(indexName);
        }
        return vectorStores.get(indexName);
    }

    public void createStore(String indexName) {
        if(!vectorStores.containsKey(indexName)){
            try {
                createVectorIndexIfNotExists(indexName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            VectorStore vectorStore = initVectorStore(indexName);
            vectorStores.put(indexName, vectorStore);
        }
    }

    @Override
    public boolean exsisteVectorStore(String indexName) {
        return vectorStores.containsKey(indexName);
    }


    private void createVectorIndexIfNotExists(String index) throws IOException {
        String body = String.format("""
                {
                  "mappings": {
                    "properties": {
                      "content": { "type": "text" },
                      "embedding": {
                        "type": "dense_vector",
                        "dims": %d,
                        "index": true,
                        "similarity": "cosine"
                      },
                      "id": {
                        "type": "keyword"
                      },
                      "metadata": {
                        "properties": {
                          "file_name": { "type": "keyword" },
                          "page_number": { "type": "long" }
                        }
                      }
                    }
                  }
                }
                """,DIMENSIONS);
        Request request = new Request("PUT", "/" + index);
        request.setJsonEntity(body);
        Response response = restClient.performRequest(request);
        System.out.println("✅ Index créé : " + response.getStatusLine());
    }

    private VectorStore initVectorStore(String indexName){
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName(indexName);    // Optional: defaults to "spring-ai-document-index"
        options.setSimilarity(SimilarityFunction.cosine);           // Optional: defaults to COSINE
        options.setDimensions(DIMENSIONS);             // Optional: defaults to model dimensions or 1536
        return  ElasticsearchVectorStore.builder(this.restClient, this.embeddingModel)
                .options(options)                     // Optional: use custom options
                .initializeSchema(true)               // Optional: defaults to false
                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
                .build();
    }

    private VectorStore loadVectorStore(String indexName) {
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName(indexName);
        options.setSimilarity(SimilarityFunction.cosine);
        options.setDimensions(DIMENSIONS);

        return ElasticsearchVectorStore.builder(this.restClient, this.embeddingModel)
                .options(options)
                .build();
    }

}