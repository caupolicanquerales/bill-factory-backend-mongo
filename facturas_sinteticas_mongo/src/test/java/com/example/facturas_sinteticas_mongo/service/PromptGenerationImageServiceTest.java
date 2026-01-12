package com.example.facturas_sinteticas_mongo.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.facturas_sinteticas_mongo.dao.PromptGenerateImageRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationImage;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class PromptGenerationImageServiceTest {

    @Mock
    private PromptGenerateImageRepository repository;

    private PromptGenerationImageService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new PromptGenerationImageService(repository);
    }

    @Test
    void saveAndUpdate_setsReturnedId() {
        PromptGenerationRequest req = new PromptGenerationRequest();
        req.setPrompt("p");
        req.setName("n");
        PromptGenerationImage saved = new PromptGenerationImage();
        saved.setId("abc");
        when(repository.save(org.mockito.ArgumentMatchers.any(PromptGenerationImage.class)))
                .thenReturn(Mono.just(saved));

        StepVerifier.create(service.saveAndUpdatePromptGeneration(req))
            .expectNextMatches(r -> "abc".equals(r.getId()))
            .verifyComplete();
    }

    @Test
    void getAll_mapsToResponse() {
        PromptGenerationImage one = new PromptGenerationImage();
        one.setId("1");
        one.setPrompt("p");
        one.setName("n");
        when(repository.findAll()).thenReturn(Flux.just(one));

        StepVerifier.create(service.getAllPromptGeneration())
                .expectNextMatches(r -> "1".equals(r.getId()))
                .verifyComplete();
    }

    @Test
    void delete_returnsEmptyResponse() {
        when(repository.deleteById("id")).thenReturn(Mono.empty());
        StepVerifier.create(service.deletePrompt("id"))
                .expectNextMatches(r -> r.getId() == null)
                .verifyComplete();
    }
}
