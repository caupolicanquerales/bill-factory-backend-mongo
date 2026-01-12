package com.example.facturas_sinteticas_mongo.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ResponseUtilTest {

    static class Wrapper {
        List<String> items;
        public void setItems(List<String> items) { this.items = items; }
    }

    @Test
    void toListResponse_wrapsEmptyList() {
        Mono<ResponseEntity<Wrapper>> mono = ResponseUtil.toListResponse(
                Flux.empty(), Wrapper::new, Wrapper::setItems);

        StepVerifier.create(mono)
                .expectNextMatches(re -> re.getBody() != null && re.getBody().items == null)
                .verifyComplete();
    }

    @Test
    void toListResponse_wrapsNonEmptyList() {
        Mono<ResponseEntity<Wrapper>> mono = ResponseUtil.toListResponse(
                Flux.just("a", "b"), Wrapper::new, Wrapper::setItems);

        StepVerifier.create(mono)
                .expectNextMatches(re -> {
                    Wrapper w = re.getBody();
                    return w != null && w.items != null && w.items.size() == 2;
                })
                .verifyComplete();
    }
}
