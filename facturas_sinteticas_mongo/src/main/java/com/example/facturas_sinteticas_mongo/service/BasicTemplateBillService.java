package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.BasicTemplateBillRepository;
import com.example.facturas_sinteticas_mongo.model.BasicTemplateBill;
import com.example.facturas_sinteticas_mongo.request.BasicTemplateRequest;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.BasicTemplateResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("basicTemplateService")
public class BasicTemplateBillService {
	
	private static final Logger LOG = LogManager.getLogger(BasicTemplateBillService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final BasicTemplateBillRepository basicTemplateBill;
	
	public BasicTemplateBillService(@Qualifier("basicTemplateBillRepository") BasicTemplateBillRepository basicTemplateBill) {
		this.basicTemplateBill= basicTemplateBill;
	}
	
	public Mono<PromptGenerationRequest> saveAndUpdateBasicTemplate(BasicTemplateRequest request) {
		BasicTemplateBill basicTemplate= mapper.convertValue(request, BasicTemplateBill.class);
		return basicTemplateBill.save(basicTemplate)
				.map(response->{
					var promptGenerated= new PromptGenerationRequest();
					promptGenerated.setId(response.getId());
					return promptGenerated;
				})
				.doOnNext(item->{
					LOG.info("Prompt generation images guardado: "+ item.getName());
				})
				.doOnError(e->{
					LOG.error("Error al guardar inperfection", e);
				});
	}
	
	
	public Flux<BasicTemplateResponse> getAllBasicTemplate() {
		return basicTemplateBill.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, BasicTemplateResponse.class))
				.map(basicTemplate->{
					basicTemplate.setCssString("");
					basicTemplate.setHtmlString("");
					return basicTemplate;
				})
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de basic template", e);
				});
	}
	
	
	public Mono<BasicTemplateResponse> deleteBasicTemplate(String id) {
		return basicTemplateBill.deleteById(id)
				.then(Mono.just(new BasicTemplateResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de basic template", e);
				});
	}
	
	public Mono<BasicTemplateResponse> findBasicTemplate(String id) {
		return basicTemplateBill.findById(id)
				.map(basicTemplate-> mapper.convertValue(basicTemplate, BasicTemplateResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de basic template", e);
				});
	}

}
