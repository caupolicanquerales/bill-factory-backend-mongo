package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllSyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PublicityDataGenerationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicityDataGenerationControllerTest {

    @Test
    void getAllPublicityGeneration_returnsWrappedList() {
        PublicityDataGenerationService service = Mockito.mock(PublicityDataGenerationService.class);
        when(service.getAllPublicityGeneration()).thenReturn(Flux.just(new SyntheticDataGenerationResponse()));

        PublicityDataGenerationController controller = new PublicityDataGenerationController(service);
        ResponseEntity<AllSyntheticDataGenerationResponse> response = controller.getAllSyntheticData().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getSynthetics().size());
    }

    @Test
    void deleteSyntheticData_returnsOkWhenDeleted() {
        PublicityDataGenerationService service = Mockito.mock(PublicityDataGenerationService.class);
        when(service.deletePublicityData("1")).thenReturn(Mono.just(new SyntheticDataGenerationResponse()));
        PublicityDataGenerationController controller = new PublicityDataGenerationController(service);

        SyntheticDataGenerationResponse body = controller.deleteSyntheticData(new com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
