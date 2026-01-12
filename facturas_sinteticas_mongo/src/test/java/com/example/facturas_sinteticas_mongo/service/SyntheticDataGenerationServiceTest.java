package com.example.facturas_sinteticas_mongo.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.facturas_sinteticas_mongo.dao.SyntheticDataGenerationRepository;
import com.example.facturas_sinteticas_mongo.model.SyntheticDataGeneration;
import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class SyntheticDataGenerationServiceTest {

    @Mock
    private SyntheticDataGenerationRepository repository;

    private SyntheticDataGenerationService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new SyntheticDataGenerationService(repository);
    }

    @Test
    void saveAndUpdate_setsReturnedId() {
        SyntheticDataGenerationRequest req = new SyntheticDataGenerationRequest();
        req.setData("d");
        req.setName("n");
        SyntheticDataGeneration saved = new SyntheticDataGeneration();
        saved.setId("abc");
        when(repository.save(org.mockito.ArgumentMatchers.any(SyntheticDataGeneration.class)))
                .thenReturn(Mono.just(saved));

        StepVerifier.create(service.saveAndUpdateSyntheticGeneration(req))
            .expectNextMatches(r -> "abc".equals(r.getId()))
            .verifyComplete();
    }

    @Test
    void getAll_mapsToResponse() {
        SyntheticDataGeneration one = new SyntheticDataGeneration();
        one.setId("1");
        one.setData("d");
        one.setName("n");
        when(repository.findAll()).thenReturn(Flux.just(one));

        StepVerifier.create(service.getAllSyntheticGeneration())
                .expectNextMatches(r -> "1".equals(r.getId()))
                .verifyComplete();
    }

    @Test
    void delete_returnsEmptyResponse() {
        when(repository.deleteById("id")).thenReturn(Mono.empty());
        StepVerifier.create(service.deleteSyntheticData("id"))
                .expectNextMatches(r -> r.getId() == null)
                .verifyComplete();
    }
}
