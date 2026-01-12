package com.example.facturas_sinteticas_mongo.service;

import com.example.facturas_sinteticas_mongo.dao.PublicityDataGenerationRepository;
import com.example.facturas_sinteticas_mongo.model.PublicityDataGeneration;
import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;
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

class PublicityDataGenerationServiceTest {

    private PublicityDataGenerationRepository repository;
    private PublicityDataGenerationService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(PublicityDataGenerationRepository.class);
        service = new PublicityDataGenerationService(repository);
    }

    @Test
    void saveAndUpdatePublicityGeneration_returnsIdFromSavedEntity() {
        SyntheticDataGenerationRequest req = new SyntheticDataGenerationRequest();
        req.setId("req-id");
        req.setData("d");
        req.setName("n");

        PublicityDataGeneration saved = new PublicityDataGeneration();
        saved.setId("saved-id");
        when(repository.save(any(PublicityDataGeneration.class))).thenReturn(Mono.just(saved));

        Mono<SyntheticDataGenerationRequest> result = service.saveAndUpdatePublicityGeneration(req);

        StepVerifier.create(result)
                .assertNext(res -> assertEquals("saved-id", res.getId()))
                .verifyComplete();

        ArgumentCaptor<PublicityDataGeneration> captor = ArgumentCaptor.forClass(PublicityDataGeneration.class);
        verify(repository).save(captor.capture());
        assertEquals(req.getName(), captor.getValue().getName());
    }

    @Test
    void getAllPublicityGeneration_mapsEntitiesToResponses() {
        PublicityDataGeneration e = new PublicityDataGeneration();
        e.setId("1");
        e.setData("data");
        e.setName("name");
        when(repository.findAll()).thenReturn(Flux.just(e));

        StepVerifier.create(service.getAllPublicityGeneration())
                .assertNext(resp -> assertEquals("1", resp.getId()))
                .verifyComplete();
    }

    @Test
    void deletePublicityData_returnsEmptyResponseMono() {
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        Mono<SyntheticDataGenerationResponse> result = service.deletePublicityData("1");
        StepVerifier.create(result)
                .assertNext(resp -> assertNull(resp.getId()))
                .verifyComplete();
    }
}
