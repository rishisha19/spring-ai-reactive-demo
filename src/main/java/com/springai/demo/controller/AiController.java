package com.springai.demo.controller;

import com.springai.demo.dto.ChatRequest;
import com.springai.demo.dto.ChatResponse;
import com.springai.demo.dto.DocumentAnalysisResponse;
import com.springai.demo.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Reactive REST Controller for Spring AI endpoints
 * Demonstrates various AI capabilities with reactive programming
 */
@Slf4j
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    /**
     * Simple chat endpoint
     * POST /api/ai/chat
     */
    @PostMapping("/chat")
    public Mono<ChatResponse> chat(@RequestBody ChatRequest request) {
        log.info("Received chat request");
        return aiService.chat(request);
    }

    /**
     * Streaming chat endpoint - returns Server-Sent Events
     * GET /api/ai/chat/stream?message=Hello
     */
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestParam String message) {
        log.info("Received streaming chat request");
        return aiService.chatStream(message);
    }

    /**
     * Context-aware chat using RAG
     * POST /api/ai/chat/context
     */
    @PostMapping("/chat/context")
    public Mono<ChatResponse> chatWithContext(@RequestBody ChatRequest request) {
        log.info("Received context-aware chat request");
        return aiService.chatWithContext(request);
    }

    /**
     * Add documents to knowledge base
     * POST /api/ai/documents
     */
    @PostMapping("/documents")
    public Mono<Map<String, String>> addDocuments(@RequestBody List<String> documents) {
        log.info("Adding documents to knowledge base");
        return aiService.addDocuments(documents)
                .map(result -> Map.of("status", "success", "message", result));
    }

    /**
     * Analyze multiple documents
     * POST /api/ai/documents/analyze
     */
    @PostMapping("/documents/analyze")
    public Mono<DocumentAnalysisResponse> analyzeDocuments(
            @RequestBody List<String> documents) {
        log.info("Analyzing documents");
        return aiService.analyzeDocuments(documents);
    }

    /**
     * Extract structured data from text
     * POST /api/ai/extract
     */
    @PostMapping("/extract")
    public Mono<Map<String, Object>> extractStructuredData(
            @RequestBody Map<String, String> request) {
        log.info("Extracting structured data");
        String text = request.get("text");
        String schema = request.get("schema");
        return aiService.extractStructuredData(text, schema);
    }

    /**
     * Health check endpoint
     * GET /api/ai/health
     */
    @GetMapping("/health")
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of(
                "status", "UP",
                "service", "Spring AI Reactive Demo",
                "framework", "Spring AI 1.0.0-M2"
        ));
    }
    
    /**
     * Demonstrate provider abstraction
     * GET /api/ai/provider/info
     */
    @GetMapping("/provider/info")
    public Map<String, Object> getProviderInfo() {
        return Map.of(
            "message", "Spring AI Provider Abstraction Demo",
            "benefit", "Write once, run with any LLM provider",
            "providers", Map.of(
                "openai", "GPT-3.5/GPT-4 models",
                "azure", "Azure OpenAI Service",
                "anthropic", "Claude 3 models",
                "ollama", "Local models (Llama2, Mistral, etc.)"
            ),
            "howToSwitch", "Change spring.ai.{provider} configuration",
            "noCodeChange", true
        );
    }
}