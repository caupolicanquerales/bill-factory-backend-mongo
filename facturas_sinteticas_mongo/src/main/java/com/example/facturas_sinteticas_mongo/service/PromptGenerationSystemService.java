package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PromptGenerateSystemRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationSystem;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("systemService")
public class PromptGenerationSystemService {
	
	private static final Logger LOG = LogManager.getLogger(PromptGenerationSystemService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final PromptGenerateSystemRepository promptGenerateSystem;
	
	public PromptGenerationSystemService(PromptGenerateSystemRepository promptGenerateSystem) {
		this.promptGenerateSystem= promptGenerateSystem;
	}
	
	public Mono<PromptGenerationRequest> saveAndUpdatePromptGeneration(PromptGenerationRequest imperfection) {
		PromptGenerationSystem promptGeneration= mapper.convertValue(imperfection, PromptGenerationSystem.class);
		return promptGenerateSystem.save(promptGeneration)
				.map(response->{
					var promptGenerated= new PromptGenerationRequest();
					promptGenerated.setId(response.getId());
					return promptGenerated;
				})
				.doOnNext(item->{
					LOG.info("Prompt generation system guardado: "+ item.getName());
				})
				.doOnError(e->{
					LOG.error("Error al guardar prompt system", e);
				});
	}
	
	
	public Flux<PromptGenerationResponse> getAllPromptGeneration() {
		return promptGenerateSystem.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, PromptGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de prompt system", e);
				});
	}
	
	
	public Mono<PromptGenerationResponse> deletePrompt(String id) {
		return promptGenerateSystem.deleteById(id)
				.then(Mono.just(new PromptGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de prompt system", e);
				});
	}

}
