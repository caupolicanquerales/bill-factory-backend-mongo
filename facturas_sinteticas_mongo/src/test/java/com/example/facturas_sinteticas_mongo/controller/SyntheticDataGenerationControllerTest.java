package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllSyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.SyntheticDataGenerationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SyntheticDataGenerationControllerTest {

    @Test
    void getAllSyntheticData_returnsWrappedList() {
        SyntheticDataGenerationService service = Mockito.mock(SyntheticDataGenerationService.class);
        when(service.getAllSyntheticGeneration()).thenReturn(Flux.just(new SyntheticDataGenerationResponse()));

        SyntheticDataGenerationController controller = new SyntheticDataGenerationController(service);
        ResponseEntity<AllSyntheticDataGenerationResponse> response = controller.getAllSyntheticData().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getSynthetics().size());
    }

    @Test
    void deleteSyntheticData_returnsOkWhenDeleted() {
        SyntheticDataGenerationService service = Mockito.mock(SyntheticDataGenerationService.class);
        when(service.deleteSyntheticData("1")).thenReturn(Mono.just(new SyntheticDataGenerationResponse()));
        SyntheticDataGenerationController controller = new SyntheticDataGenerationController(service);

        SyntheticDataGenerationResponse body = controller.deleteSyntheticData(new com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
