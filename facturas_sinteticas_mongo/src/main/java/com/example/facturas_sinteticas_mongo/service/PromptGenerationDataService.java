package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PromptGenerateDataRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationData;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("dataService")
public class PromptGenerationDataService {
	
	private static final Logger LOG = LogManager.getLogger(PromptGenerationDataService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private PromptGenerateDataRepository promptGenerateData; 
	
	
	public PromptGenerationDataService(@Qualifier("promptGenerateDataRepository") PromptGenerateDataRepository promptGenerateData) {
		this.promptGenerateData= promptGenerateData;
	}
	
	
	
	public Mono<PromptGenerationRequest> saveAndUpdatePromptGeneration(PromptGenerationRequest imperfection) {
		PromptGenerationData promptGeneration= mapper.convertValue(imperfection, PromptGenerationData.class);
		return promptGenerateData.save(promptGeneration)
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
		return promptGenerateData.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, PromptGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	public Mono<PromptGenerationResponse> deletePrompt(String id) {
		return promptGenerateData.deleteById(id)
				.then(Mono.just(new PromptGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
}
