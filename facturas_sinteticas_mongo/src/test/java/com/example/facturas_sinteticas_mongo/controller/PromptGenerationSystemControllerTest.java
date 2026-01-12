package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllPromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PromptGenerationSystemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromptGenerationSystemControllerTest {

    @Test
    void getAllImperfections_returnsWrappedList() {
        PromptGenerationSystemService service = Mockito.mock(PromptGenerationSystemService.class);
        when(service.getAllPromptGeneration()).thenReturn(Flux.just(new PromptGenerationResponse()));

        PromptGenerationSystemController controller = new PromptGenerationSystemController(service);
        ResponseEntity<AllPromptGenerationResponse> response = controller.getAllImperfections().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPrompts().size());
    }

    @Test
    void deleteImperfection_returnsOkWhenDeleted() {
        PromptGenerationSystemService service = Mockito.mock(PromptGenerationSystemService.class);
        when(service.deletePrompt("1")).thenReturn(Mono.just(new PromptGenerationResponse()));
        PromptGenerationSystemController controller = new PromptGenerationSystemController(service);

        PromptGenerationResponse body = controller.deleteImperfection(new com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
