package com.springai.demo.service;

import com.springai.demo.dto.ChatRequest;
import com.springai.demo.dto.ChatResponse;
import com.springai.demo.dto.DocumentAnalysisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AI Service demonstrating Spring AI capabilities with reactive programming
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatModel chatModel;
    private final VectorStore vectorStore;

    /**
     * Simple chat completion with reactive support
     */
    public Mono<ChatResponse> chat(ChatRequest request) {
        return Mono.fromCallable(() -> {
            log.info("Processing chat request");
            
            String systemPrompt = """
                You are a helpful AI assistant powered by Spring AI.
                Provide clear, concise, and accurate responses.
                """;
            
            Message systemMessage = new SystemMessage(systemPrompt);
            Message userMessage = new UserMessage(request.getMessage());
            
            Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
            org.springframework.ai.chat.model.ChatResponse aiResponse = chatModel.call(prompt);
            
            return ChatResponse.builder()
                    .response(aiResponse.getResult().getOutput().getContent())
                    .model("gpt-4")
                    .timestamp(LocalDateTime.now())
                    .tokensUsed(100) // placeholder
                    .build();
        });
    }

    /**
     * Streaming chat response - returns words as they're generated
     */
    public Flux<String> chatStream(String message) {
        log.info("Streaming chat request: {}", message);
        
        return Flux.create(sink -> {
            try {
                Prompt prompt = new Prompt(new UserMessage(message));
                Flux<org.springframework.ai.chat.model.ChatResponse> streamResponse = chatModel.stream(prompt);
                
                streamResponse.subscribe(
                    response -> {
                        String content = response.getResult()
                                .getOutput()
                                .getContent();
                        sink.next(content);
                    },
                    error -> {
                        log.error("Error in streaming: ", error);
                        sink.error(error);
                    },
                    sink::complete
                );
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    /**
     * Context-aware chat using RAG (Retrieval Augmented Generation)
     */
    public Mono<ChatResponse> chatWithContext(ChatRequest request) {
        return Mono.fromCallable(() -> {
            log.info("Processing context-aware chat: {}", request.getMessage());
            
            // Search for relevant documents in vector store
            List<Document> relevantDocs = vectorStore.similaritySearch(
                    request.getMessage());
            
            String context = relevantDocs.stream()
                    .map(Document::getContent)
                    .collect(Collectors.joining("\n\n"));
            
            String enhancedPrompt = String.format("""
                Context information:
                %s
                
                User question: %s
                
                Please answer based on the context provided.
                """, context, request.getMessage());
            
            Prompt prompt = new Prompt(new UserMessage(enhancedPrompt));
            org.springframework.ai.chat.model.ChatResponse aiResponse = chatModel.call(prompt);
            
            return ChatResponse.builder()
                    .response(aiResponse.getResult().getOutput().getContent())
                    .model("gpt-4")
                    .timestamp(LocalDateTime.now())
                    .tokensUsed(100) // placeholder
                    .build();
        });
    }

    /**
     * Add documents to vector store for RAG
     */
    public Mono<String> addDocuments(List<String> documents) {
        return Mono.fromCallable(() -> {
            log.info("Adding {} documents to vector store", documents.size());
            
            List<Document> docs = documents.stream()
                    .map(content -> new Document(content))
                    .collect(Collectors.toList());
            
            vectorStore.add(docs);
            
            return String.format("Successfully added %d documents", documents.size());
        });
    }

    /**
     * Analyze multiple documents and provide insights
     */
    public Mono<DocumentAnalysisResponse> analyzeDocuments(List<String> documents) {
        return Mono.fromCallable(() -> {
            log.info("Analyzing {} documents", documents.size());
            
            String combinedContent = String.join("\n\n", documents);
            
            String analysisPrompt = String.format("""
                Analyze the following documents and provide:
                1. A brief summary (2-3 sentences)
                2. 3-5 key points
                3. Overall sentiment (positive/negative/neutral)
                
                Documents:
                %s
                
                Format your response as:
                SUMMARY: [your summary]
                KEY_POINTS: [point1]|[point2]|[point3]
                SENTIMENT: [sentiment]
                """, combinedContent);
            
            Prompt prompt = new Prompt(new UserMessage(analysisPrompt));
            org.springframework.ai.chat.model.ChatResponse response = chatModel.call(prompt);
            String content = response.getResult().getOutput().getContent();
            
            // Parse response
            String summary = extractSection(content, "SUMMARY:");
            List<String> keyPoints = List.of(
                    extractSection(content, "KEY_POINTS:").split("\\|"));
            String sentiment = extractSection(content, "SENTIMENT:");
            
            return DocumentAnalysisResponse.builder()
                    .summary(summary)
                    .keyPoints(keyPoints)
                    .sentiment(sentiment)
                    .documentCount(documents.size())
                    .build();
        });
    }

    /**
     * Generate structured data from natural language
     */
    public Mono<Map<String, Object>> extractStructuredData(String text, String schema) {
        return Mono.fromCallable(() -> {
            log.info("Extracting structured data from text");
            
            String extractionPrompt = String.format("""
                Extract information from the following text according to this schema:
                %s
                
                Text:
                %s
                
                Provide the output as a valid JSON object.
                """, schema, text);
            
            Prompt prompt = new Prompt(new UserMessage(extractionPrompt));
            org.springframework.ai.chat.model.ChatResponse response = chatModel.call(prompt);
            
            // In a real implementation, parse JSON response
            return Map.of(
                    "extracted", response.getResult().getOutput().getContent(),
                    "timestamp", LocalDateTime.now().toString()
            );
        });
    }

    private String extractSection(String content, String marker) {
        int start = content.indexOf(marker);
        if (start == -1) return "";
        
        start += marker.length();
        int end = content.indexOf("\n", start);
        if (end == -1) end = content.length();
        
        return content.substring(start, end).trim();
    }
}