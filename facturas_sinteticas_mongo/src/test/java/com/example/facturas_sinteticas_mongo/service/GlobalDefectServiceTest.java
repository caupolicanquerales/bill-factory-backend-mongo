package com.example.facturas_sinteticas_mongo.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.facturas_sinteticas_mongo.dao.GlobalDefectMongoRepository;
import com.example.facturas_sinteticas_mongo.model.GlobalDefect;
import com.example.facturas_sinteticas_mongo.request.GlobalDefectRequest;
import com.example.facturas_sinteticas_mongo.response.GlobalDefectResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class GlobalDefectServiceTest {

    @Mock
    private GlobalDefectMongoRepository repository;

    private GlobalDefectService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new GlobalDefectService(repository);
    }

    @Test
    void saveAndUpdate_savesEntity() {
        GlobalDefectRequest req = new GlobalDefectRequest();
        req.setDefect("d");
        GlobalDefect saved = new GlobalDefect();
        saved.setId("abc");
        saved.setDefect("d");
        when(repository.save(org.mockito.ArgumentMatchers.any(GlobalDefect.class)))
                .thenReturn(Mono.just(saved));

        StepVerifier.create(service.saveAndUpdateGlobalDefect(req))
                .expectNextMatches(g -> "abc".equals(g.getId()))
                .verifyComplete();
    }

    @Test
    void getAll_mapsToResponse() {
        GlobalDefect one = new GlobalDefect();
        one.setId("1");
        one.setDefect("d");
        when(repository.findAll()).thenReturn(Flux.just(one));

        StepVerifier.create(service.getAllGlobalDefect())
                .expectNextMatches(r -> "1".equals(r.getId()))
                .verifyComplete();
    }

    @Test
    void delete_returnsEmptyResponse() {
        when(repository.deleteById("id")).thenReturn(Mono.empty());
        StepVerifier.create(service.deleteGlobalDefect("id"))
                .expectNextMatches(r -> r.getId() == null)
                .verifyComplete();
    }
}
