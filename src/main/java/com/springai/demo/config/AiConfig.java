package com.springai.demo.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Spring AI Configuration
 * Sets up vector store and other AI components
 */
@Configuration
public class AiConfig {

    /**
     * Configure RestClient.Builder for Spring AI OpenAI integration
     */
    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Configure in-memory vector store for RAG
     * In production, consider using Pinecone, Weaviate, or Chroma
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new SimpleVectorStore(embeddingModel);
    }
}