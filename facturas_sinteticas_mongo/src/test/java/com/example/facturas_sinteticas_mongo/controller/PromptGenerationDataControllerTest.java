package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllPromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PromptGenerationDataService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromptGenerationDataControllerTest {

    @Test
    void getAllDataPrompts_returnsWrappedList() {
        PromptGenerationDataService service = Mockito.mock(PromptGenerationDataService.class);
        when(service.getAllPromptGeneration()).thenReturn(Flux.just(new PromptGenerationResponse()));

        PromptGenerationDataController controller = new PromptGenerationDataController(service);
        ResponseEntity<AllPromptGenerationResponse> response = controller.getAllDataPrompts().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPrompts().size());
    }

    @Test
    void deleteDataPrompt_returnsOkWhenDeleted() {
        PromptGenerationDataService service = Mockito.mock(PromptGenerationDataService.class);
        when(service.deletePrompt("1")).thenReturn(Mono.just(new PromptGenerationResponse()));
        PromptGenerationDataController controller = new PromptGenerationDataController(service);

        PromptGenerationResponse body = controller.deleteDataPrompt(new com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
