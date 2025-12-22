package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PromptGenerateImageRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationImage;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("imageService")
public class PromptGenerationImageService {
	
	private static final Logger LOG = LogManager.getLogger(PromptGenerationImageService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private PromptGenerateImageRepository promptGenerateImage; 
	
	public PromptGenerationImageService(@Qualifier("promptGenerateImageRepository") PromptGenerateImageRepository promptGenerateImage) {
		this.promptGenerateImage= promptGenerateImage;
	}
	
	public Mono<PromptGenerationRequest> saveAndUpdatePromptGeneration(PromptGenerationRequest imperfection) {
		PromptGenerationImage promptGeneration= mapper.convertValue(imperfection, PromptGenerationImage.class);
		return promptGenerateImage.save(promptGeneration)
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
		return promptGenerateImage.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, PromptGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	public Mono<PromptGenerationResponse> deletePrompt(String id) {
		return promptGenerateImage.deleteById(id)
				.then(Mono.just(new PromptGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
}
