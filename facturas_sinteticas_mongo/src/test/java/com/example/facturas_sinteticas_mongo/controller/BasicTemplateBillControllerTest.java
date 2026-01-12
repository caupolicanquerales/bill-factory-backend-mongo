package com.example.facturas_sinteticas_mongo.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.facturas_sinteticas_mongo.response.AllBasicTemplateResponse;
import com.example.facturas_sinteticas_mongo.response.BasicTemplateResponse;
import com.example.facturas_sinteticas_mongo.service.BasicTemplateBillService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BasicTemplateBillControllerTest {

    @Mock
    private BasicTemplateBillService service;

    private BasicTemplateBillController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new BasicTemplateBillController(service);
    }

    @Test
    void getAllBasicTemplate_returnsWrapper() {
        BasicTemplateResponse res = new BasicTemplateResponse();
        res.setId("1");
        res.setCssString("");
        res.setHtmlString("");
        res.setName("n");
        when(service.getAllBasicTemplate()).thenReturn(Flux.just(res));

        Mono<ResponseEntity<AllBasicTemplateResponse>> mono = controller.getAllBasicTemplates();
        StepVerifier.create(mono)
                .expectNextMatches(re -> {
                    AllBasicTemplateResponse body = re.getBody();
                    return body != null && body.getBasicTemplates() != null
                            && body.getBasicTemplates().size() == 1
                            && "1".equals(body.getBasicTemplates().get(0).getId());
                })
                .verifyComplete();
    }
}
