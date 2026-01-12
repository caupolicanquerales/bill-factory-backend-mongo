package com.example.facturas_sinteticas_mongo.service.sse;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SseGenerationDataServiceTest {

    @Test
    void sendMessage_emitsToSubscribers() {
        SseGenerationDataService sse = new SseGenerationDataService();
        Flux<String> stream = sse.getDataMessageStream();

        StepVerifier.create(stream.take(1))
                .then(() -> sse.sendMessage("hi"))
                .expectNext("hi")
                .verifyComplete();
    }
}
