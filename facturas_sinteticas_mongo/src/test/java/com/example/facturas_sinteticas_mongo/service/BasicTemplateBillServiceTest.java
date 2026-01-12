package com.example.facturas_sinteticas_mongo.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.facturas_sinteticas_mongo.dao.BasicTemplateBillRepository;
import com.example.facturas_sinteticas_mongo.model.BasicTemplateBill;
import com.example.facturas_sinteticas_mongo.request.BasicTemplateRequest;
import com.example.facturas_sinteticas_mongo.response.BasicTemplateResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BasicTemplateBillServiceTest {

    @Mock
    private BasicTemplateBillRepository repository;

    private BasicTemplateBillService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new BasicTemplateBillService(repository);
    }

    @Test
    void saveAndUpdateBasicTemplate_setsReturnedId() {
        BasicTemplateRequest req = new BasicTemplateRequest();
        req.setHtmlString("<div />");
        req.setCssString("div{}");
        req.setName("n");

        BasicTemplateBill saved = new BasicTemplateBill();
        saved.setId("abc");
        when(repository.save(org.mockito.ArgumentMatchers.any(BasicTemplateBill.class)))
                .thenReturn(Mono.just(saved));

        StepVerifier.create(service.saveAndUpdateBasicTemplate(req))
                .expectNextMatches(r -> "abc".equals(r.getId()))
                .verifyComplete();
    }

    @Test
    void getAllBasicTemplate_mapsToResponseAndClearsStrings() {
        BasicTemplateBill one = new BasicTemplateBill();
        one.setId("1");
        one.setCssString("css");
        one.setHtmlString("html");
        one.setName("n");
        when(repository.findAll()).thenReturn(Flux.just(one));

        StepVerifier.create(service.getAllBasicTemplate())
            .expectNextMatches(r -> "1".equals(r.getId())
                && "".equals(r.getCssString())
                && "".equals(r.getHtmlString()))
            .verifyComplete();
    }

    @Test
    void deleteBasicTemplate_returnsEmptyResponse() {
        when(repository.deleteById("id")).thenReturn(Mono.empty());
        StepVerifier.create(service.deleteBasicTemplate("id"))
                .expectNextMatches(r -> r.getId() == null)
                .verifyComplete();
    }

    @Test
    void findBasicTemplate_mapsToResponse() {
        BasicTemplateBill one = new BasicTemplateBill();
        one.setId("1");
        when(repository.findById("1")).thenReturn(Mono.just(one));

        StepVerifier.create(service.findBasicTemplate("1"))
                .expectNextMatches(r -> "1".equals(r.getId()))
                .verifyComplete();
    }
}
