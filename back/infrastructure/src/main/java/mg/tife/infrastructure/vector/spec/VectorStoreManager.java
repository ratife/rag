package mg.tife.infrastructure.vector.spec;

import org.springframework.ai.vectorstore.VectorStore;


public interface VectorStoreManager {

    public VectorStore getVectorStore(String indexName);

    public void createStore(String indexName);

    public boolean exsisteVectorStore(String indexName);
}