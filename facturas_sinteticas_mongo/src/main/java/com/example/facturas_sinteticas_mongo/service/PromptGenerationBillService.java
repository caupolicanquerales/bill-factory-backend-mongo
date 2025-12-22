package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PromptGenerateBillRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationBill;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("billService")
public class PromptGenerationBillService {
	
	private static final Logger LOG = LogManager.getLogger(PromptGenerationBillService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final PromptGenerateBillRepository promptGenerateBill;
	
	public PromptGenerationBillService(PromptGenerateBillRepository promptGenerateBill) {
		this.promptGenerateBill= promptGenerateBill;
	}
	
	public Mono<PromptGenerationRequest> saveAndUpdatePromptGeneration(PromptGenerationRequest imperfection) {
		PromptGenerationBill promptGeneration= mapper.convertValue(imperfection, PromptGenerationBill.class);
		return promptGenerateBill.save(promptGeneration)
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
	
	
	public Flux<PromptGenerationResponse> getAllPromptGeneration() {
		return promptGenerateBill.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, PromptGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	public Mono<PromptGenerationResponse> deletePrompt(String id) {
		return promptGenerateBill.deleteById(id)
				.then(Mono.just(new PromptGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}

}
