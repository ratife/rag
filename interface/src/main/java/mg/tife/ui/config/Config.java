package mg.tife.ui.config;

import org.springframework.context.annotation.Configuration;


/**
 * Configuration for the vector store.
 * Provides both in-memory implementation for testing and Elasticsearch implementation for production.
 */
@Configuration
public class Config {

    /**
     * Provides an in-memory VectorStore bean for development and testing.
     *
     * @return An in-memory VectorStore implementation

     @Bean
     @Profile({"dev", "test"})
     public VectorStore inMemoryVectorStore() {
     return new InMemoryVectorStore();
     }*/

    /*** For production, use the Elasticsearch implementation.
     * This requires the following dependencies in your pom.xml:
     * - org.springframework.ai:spring-ai-elasticsearch-store
     * - org.elasticsearch.client:elasticsearch-rest-client
     *
     * And the following configuration in your application.yml:
     * spring:
     *   elasticsearch:
     *     uris: http://elasticsearch:9200
     *
     * @return An Elasticsearch VectorStore implementation
     */
    /*
    @Bean
    @Profile("prod")
    public VectorStore elasticsearchVectorStore(RestClient restClient, EmbeddingModel embeddingModel) {
        ElasticsearchVectorStoreOptions options = new ElasticsearchVectorStoreOptions();
        options.setIndexName("rag-document-index");  // Custom index name
        options.setSimilarity(COSINE);               // Use cosine similarity
        options.setDimensions(1536);                 // Set dimensions for embeddings

        return ElasticsearchVectorStore.builder(restClient, embeddingModel)
                .options(options)
                .initializeSchema(true)              // Create index if it doesn't exist
                .build();
    }
    */
}
