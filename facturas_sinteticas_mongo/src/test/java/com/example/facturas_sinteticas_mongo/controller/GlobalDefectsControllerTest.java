package com.example.facturas_sinteticas_mongo.controller;

import com.example.facturas_sinteticas_mongo.response.AllGlobalDefectResponse;
import com.example.facturas_sinteticas_mongo.response.GlobalDefectResponse;
import com.example.facturas_sinteticas_mongo.service.GlobalDefectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalDefectsControllerTest {

    @Test
    void getAllGlobalDefect_returnsWrappedList() {
        GlobalDefectService service = Mockito.mock(GlobalDefectService.class);
        when(service.getAllGlobalDefect()).thenReturn(Flux.just(new GlobalDefectResponse()));

        GlobalDefectsController controller = new GlobalDefectsController(service);
        ResponseEntity<AllGlobalDefectResponse> response = controller.getAllGlobalDefect().block();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getDefects().size());
    }

    @Test
    void deleteGlobalDefect_returnsOkWhenDeleted() {
        GlobalDefectService service = Mockito.mock(GlobalDefectService.class);
        when(service.deleteGlobalDefect("1")).thenReturn(Mono.just(new GlobalDefectResponse()));
        GlobalDefectsController controller = new GlobalDefectsController(service);

        GlobalDefectResponse body = controller.deleteGlobalDefect(new com.example.facturas_sinteticas_mongo.request.GlobalDefectRequest(){
            { setId("1"); }
        }).block().getBody();
        assertNotNull(body);
    }
}
