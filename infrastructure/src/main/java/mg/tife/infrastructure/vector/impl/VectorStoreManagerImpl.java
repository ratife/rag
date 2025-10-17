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
    private RestClient restClient;
    private EmbeddingModel embeddingModel;
    private DocumentJpaRepository documentJpaRepository;

    VectorStoreManagerImpl(RestClient restClient, EmbeddingModel embeddingModel, DocumentJpaRepository documentJpaRepository){
        this.restClient = restClient;
        this.embeddingModel = embeddingModel;
        this.documentJpaRepository = documentJpaRepository;

    }

    @PostConstruct
    public void init() {
        List<DocumentEntity> listDoc = this.documentJpaRepository.findAll();
        listDoc.forEach(e->{
            System.out.println("getIndexName : "+ e.getFileName() + " , " + e.getIndexName().toString());
            VectorStore vectorStore = initVectorStore(e.getIndexName().toString());
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
        String body = """
                {
                  "mappings": {
                    "properties": {
                      "content": { "type": "text" },
                      "embedding": { "type": "dense_vector", "dims": 1024 },
                      "metadata": { "type": "object", "enabled": true }
                    }
                  }
                }
                """;
        Request request = new Request("PUT", "/" + index);
        request.setJsonEntity(body);
        Response response = restClient.performRequest(request);
        System.out.println("✅ Index créé : " + response.getStatusLine());
    }

    private VectorStore initVectorStore(String indexName){
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName(indexName);    // Optional: defaults to "spring-ai-document-index"
        options.setSimilarity(SimilarityFunction.cosine);           // Optional: defaults to COSINE
        options.setDimensions(1024);             // Optional: defaults to model dimensions or 1536
        VectorStore vectorStore =  ElasticsearchVectorStore.builder(this.restClient, this.embeddingModel)
                .options(options)                     // Optional: use custom options
                .initializeSchema(true)               // Optional: defaults to false
                .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
                .build();
        return vectorStore;
    }
}