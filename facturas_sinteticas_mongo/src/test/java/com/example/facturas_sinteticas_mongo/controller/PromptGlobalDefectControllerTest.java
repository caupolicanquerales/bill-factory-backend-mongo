package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllPromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PromptGlobalDefectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromptGlobalDefectControllerTest {

    @Test
    void getAllPromptGlobalDefect_returnsWrappedList() {
        PromptGlobalDefectService service = Mockito.mock(PromptGlobalDefectService.class);
        when(service.getAllPromptGlobalDefect()).thenReturn(Flux.just(new PromptGenerationResponse()));

        PromptGlobalDefectController controller = new PromptGlobalDefectController(service);
        ResponseEntity<AllPromptGenerationResponse> response = controller.getAllPromptGlobalDefect().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getPrompts().size());
    }

    @Test
    void deletePromptGlobalDefect_returnsOkWhenDeleted() {
        PromptGlobalDefectService service = Mockito.mock(PromptGlobalDefectService.class);
        when(service.deletePromptGlobalDefect("1")).thenReturn(Mono.just(new PromptGenerationResponse()));
        PromptGlobalDefectController controller = new PromptGlobalDefectController(service);

        PromptGenerationResponse body = controller.deletePromptGlobalDefect(new com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
