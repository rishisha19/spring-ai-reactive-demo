package com.springai.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring AI Reactive Demo Application
 * Demonstrates the power of Spring AI with reactive programming
 * 
 * Key Features:
 * - Multi-provider support (OpenAI, Azure, Anthropic, Ollama)
 * - Provider switching via configuration only
 * - Reactive programming with WebFlux
 * - Unified ChatModel interface for all providers
 */
@Slf4j
@SpringBootApplication
public class SpringAiReactiveDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiReactiveDemoApplication.class, args);
    }
    
    @Bean
    CommandLineRunner startup() {
        return args -> {
            log.info("🚀 Spring AI Multi-Provider Demo Started!");
            log.info("📝 Available Endpoints:");
            log.info("   GET  /api/ai/health - Health check");
            log.info("   GET  /api/ai/provider/info - Provider abstraction info"); 
            log.info("   POST /api/ai/chat - Chat with any LLM provider");
            log.info("   GET  /api/ai/chat/stream - Streaming responses");
            log.info("🔄 Switch providers by changing 'ai.provider' property");
            log.info("📖 See QUICK_START.md for testing instructions");
        };
    }
}