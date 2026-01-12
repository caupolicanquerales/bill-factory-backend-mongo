package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllPromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PromptGenerationImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromptGenerationImageControllerTest {

    @Test
    void getAllPrompts_returnsWrappedList() {
        PromptGenerationImageService service = Mockito.mock(PromptGenerationImageService.class);
        when(service.getAllPromptGeneration()).thenReturn(Flux.just(new PromptGenerationResponse()));

        PromptGenerationImageController controller = new PromptGenerationImageController(service);
        ResponseEntity<AllPromptGenerationResponse> response = controller.getAllPrompts().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPrompts().size());
    }

    @Test
    void deletePrompt_returnsOkWhenDeleted() {
        PromptGenerationImageService service = Mockito.mock(PromptGenerationImageService.class);
        when(service.deletePrompt("1")).thenReturn(Mono.just(new PromptGenerationResponse()));
        PromptGenerationImageController controller = new PromptGenerationImageController(service);

        PromptGenerationResponse body = controller.deletePrompt(new com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
