# Spring AI Reactive Demo 🚀

A comprehensive demonstration of Spring AI capabilities with reactive programming, showcasing the future of AI-powered Spring Boot applications.

## 🌟 Features

- **Reactive AI Chat**: Non-blocking chat completions using Project Reactor
- **Streaming Responses**: Real-time streaming of AI responses via Server-Sent Events
- **RAG (Retrieval Augmented Generation)**: Context-aware conversations using vector embeddings
- **Document Analysis**: Multi-document analysis with key insights extraction
- **Structured Data Extraction**: Convert natural language to structured JSON
- **Production-Ready**: Built with best practices and architectural patterns

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Spring Boot Application                   │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐  │
│  │              │    │              │    │              │  │
│  │  Controller  │───▶│   Service    │───▶│  Spring AI   │  │
│  │   (Reactive) │    │   Layer      │    │   Clients    │  │
│  │              │    │              │    │              │  │
│  └──────────────┘    └──────────────┘    └──────┬───────┘  │
│         │                                        │           │
│         │                                        │           │
│    ┌────▼─────┐                        ┌────────▼────────┐  │
│    │  REST    │                        │  Vector Store   │  │
│    │  API     │                        │  (Embeddings)   │  │
│    └──────────┘                        └─────────────────┘  │
│                                                               │
└───────────────────────────────┬───────────────────────────────┘
                                │
                    ┌───────────▼──────────┐
                    │   OpenAI API         │
                    │   - GPT-4            │
                    │   - Embeddings       │
                    └──────────────────────┘
```

## 🎯 Key Concepts

### What is Spring AI?

Spring AI is Spring's answer to the AI revolution, providing:
- **Model Abstraction**: Unified API across different AI providers
- **Portable AI Components**: Switch between OpenAI, Azure OpenAI, Hugging Face
- **Spring Integration**: Seamless integration with Spring ecosystem
- **Production Features**: Observability, testing, and monitoring

### Why Reactive?

Reactive programming with Spring WebFlux offers:
- **Non-blocking I/O**: Handle thousands of concurrent AI requests
- **Backpressure**: Control the flow of streaming responses
- **Resource Efficiency**: Optimal resource utilization
- **Scalability**: Scale to handle enterprise workloads

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- OpenAI API Key

### Setup

1. **Clone the repository**
```bash
git clone https://github.com/rishisha19/spring-ai-reactive-demo.git
cd spring-ai-reactive-demo
```

2. **Set your OpenAI API key**
```bash
export OPENAI_API_KEY='your-api-key-here'
```

3. **Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### 1. Simple Chat
```bash
curl -X POST http://localhost:8080/api/ai/chat \
  -H "Content-Type: application/json" \
  -d '{
    "message": "Explain Spring AI in simple terms"
  }'
```

### 2. Streaming Chat (SSE)
```bash
curl -N http://localhost:8080/api/ai/chat/stream?message=Tell%20me%20a%20story
```

### 3. Add Documents to Knowledge Base
```bash
curl -X POST http://localhost:8080/api/ai/documents \
  -H "Content-Type: application/json" \
  -d '[
    "Spring AI provides portable AI APIs for Java developers",
    "It supports multiple LLM providers including OpenAI and Azure",
    "Vector stores enable semantic search capabilities"
  ]'
```

### 4. Context-Aware Chat (RAG)
```bash
curl -X POST http://localhost:8080/api/ai/chat/context \
  -H "Content-Type: application/json" \
  -d '{
    "message": "What providers does Spring AI support?"
  }'
```

### 5. Document Analysis
```bash
curl -X POST http://localhost:8080/api/ai/documents/analyze \
  -H "Content-Type: application/json" \
  -d '[
    "Q1 revenue exceeded expectations with 25% growth",
    "Customer satisfaction scores improved to 4.8/5",
    "Successfully launched 3 new product features"
  ]'
```

### 6. Extract Structured Data
```bash
curl -X POST http://localhost:8080/api/ai/extract \
  -H "Content-Type: application/json" \
  -d '{
    "text": "John Doe, born on Jan 15, 1990, works as a Software Engineer at TechCorp",
    "schema": "{ name: string, birthDate: date, occupation: string, company: string }"
  }'
```

## 🏛️ Project Structure

```
spring-ai-reactive-demo/
├── src/
│   ├── main/
│   │   ├── java/com/springai/demo/
│   │   │   ├── SpringAiReactiveDemoApplication.java
│   │   │   ├── config/
│   │   │   │   └── AiConfig.java
│   │   │   ├── controller/
│   │   │   │   └── AiController.java
│   │   │   ├── service/
│   │   │   │   └── AiService.java
│   │   │   └── dto/
│   │   │       ├── ChatRequest.java
│   │   │       ├── ChatResponse.java
│   │   │       └── DocumentAnalysisResponse.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── pom.xml
```

## 🔑 Key Technologies

- **Spring Boot 3.3.5**: Latest Spring Boot with native support
- **Spring AI 1.0.0-M3**: Cutting-edge AI integration
- **Spring WebFlux**: Reactive web framework
- **Project Reactor**: Reactive programming foundation
- **OpenAI GPT-4**: State-of-the-art language model
- **Vector Store**: Semantic search capabilities

## 🎨 Use Cases

### 1. Intelligent Customer Support
Build chatbots that understand context and provide accurate responses using RAG.

### 2. Document Processing
Analyze contracts, reports, and documents at scale with AI-powered insights.

### 3. Content Generation
Generate marketing content, product descriptions, and documentation.

### 4. Data Extraction
Convert unstructured text into structured data for analytics and processing.

### 5. Real-time Recommendations
Provide personalized recommendations based on user context and history.

## 🔧 Configuration

### Vector Store Options

The demo uses `SimpleVectorStore` for simplicity. For production:

```java
// Pinecone
@Bean
public VectorStore vectorStore(EmbeddingClient embeddingClient) {
    return new PineconeVectorStore(embeddingClient, pineconeConfig);
}

// Weaviate
@Bean
public VectorStore vectorStore(EmbeddingClient embeddingClient) {
    return new WeaviateVectorStore(embeddingClient, weaviateConfig);
}

// Chroma
@Bean
public VectorStore vectorStore(EmbeddingClient embeddingClient) {
    return new ChromaVectorStore(embeddingClient, chromaConfig);
}
```

### Model Configuration

```yaml
spring:
  ai:
    openai:
      chat:
        options:
          model: gpt-4-turbo-preview  # or gpt-3.5-turbo for cost savings
          temperature: 0.7             # 0.0 - 1.0
          max-tokens: 1000             # Response length limit
          top-p: 1.0                   # Nucleus sampling
```

## 📊 Monitoring and Observability

Spring AI integrates with Spring Boot Actuator:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  metrics:
    tags:
      application: spring-ai-demo
```

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 🚀 Deployment

### Docker
```dockerfile
FROM eclipse-temurin:17-jre
COPY target/spring-ai-reactive-demo-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Kubernetes
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: spring-ai-demo
spec:
  replicas: 3
  template:
    spec:
      containers:
      - name: app
        image: spring-ai-demo:latest
        env:
        - name: OPENAI_API_KEY
          valueFrom:
            secretKeyRef:
              name: openai-secret
              key: api-key
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is licensed under the MIT License.

## 🔗 Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [Spring WebFlux Guide](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
- [OpenAI API Reference](https://platform.openai.com/docs/api-reference)

## 📧 Contact

For questions and support, please open an issue on GitHub.

---

**Built with ❤️ using Spring AI**