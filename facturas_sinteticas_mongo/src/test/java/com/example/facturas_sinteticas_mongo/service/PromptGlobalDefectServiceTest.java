package com.example.facturas_sinteticas_mongo.service;

import com.example.facturas_sinteticas_mongo.dao.PromptGlobalDefectRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGlobalDefect;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PromptGlobalDefectServiceTest {

    private PromptGlobalDefectRepository repository;
    private PromptGlobalDefectService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(PromptGlobalDefectRepository.class);
        service = new PromptGlobalDefectService(repository);
    }

    @Test
    void saveAndUpdatePromptGlobalDefect_returnsIdFromSavedEntity() {
        PromptGenerationRequest req = new PromptGenerationRequest();
        req.setId("req-id");
        req.setPrompt("p");
        req.setName("n");

        PromptGlobalDefect saved = new PromptGlobalDefect();
        saved.setId("saved-id");
        when(repository.save(any(PromptGlobalDefect.class))).thenReturn(Mono.just(saved));

        Mono<PromptGenerationRequest> result = service.saveAndUpdatePromptGlobalDefect(req);

        StepVerifier.create(result)
                .assertNext(res -> assertEquals("saved-id", res.getId()))
                .verifyComplete();

        ArgumentCaptor<PromptGlobalDefect> captor = ArgumentCaptor.forClass(PromptGlobalDefect.class);
        verify(repository).save(captor.capture());
        assertEquals(req.getName(), captor.getValue().getName());
    }

    @Test
    void getAllPromptGlobalDefect_mapsEntitiesToResponses() {
        PromptGlobalDefect e = new PromptGlobalDefect();
        e.setId("1");
        e.setPrompt("prompt");
        e.setName("name");
        when(repository.findAll()).thenReturn(Flux.just(e));

        StepVerifier.create(service.getAllPromptGlobalDefect())
                .assertNext(resp -> {
                    assertEquals("1", resp.getId());
                })
                .verifyComplete();
    }

    @Test
    void deletePromptGlobalDefect_returnsEmptyResponseMono() {
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        Mono<PromptGenerationResponse> result = service.deletePromptGlobalDefect("1");
        StepVerifier.create(result)
                .assertNext(resp -> assertNull(resp.getId()))
                .verifyComplete();
    }
}
