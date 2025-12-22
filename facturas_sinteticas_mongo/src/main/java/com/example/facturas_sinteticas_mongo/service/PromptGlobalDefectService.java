package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PromptGlobalDefectRepository;
import com.example.facturas_sinteticas_mongo.model.PromptGenerationBill;
import com.example.facturas_sinteticas_mongo.model.PromptGlobalDefect;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("promptGlobalDefectService")
public class PromptGlobalDefectService {
	
	private static final Logger LOG = LogManager.getLogger(PromptGlobalDefectService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final PromptGlobalDefectRepository promptGlobalDefect;
	
	public PromptGlobalDefectService(PromptGlobalDefectRepository promptGlobalDefect) {
		this.promptGlobalDefect= promptGlobalDefect;
	}
	
	public Mono<PromptGenerationRequest> saveAndUpdatePromptGlobalDefect(PromptGenerationRequest imperfection) {
		PromptGlobalDefect promptGeneration= mapper.convertValue(imperfection, PromptGlobalDefect.class);
		return promptGlobalDefect.save(promptGeneration)
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
	
	
	public Flux<PromptGenerationResponse> getAllPromptGlobalDefect() {
		return promptGlobalDefect.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, PromptGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de Prompt global defect", e);
				});
	}
	
	
	public Mono<PromptGenerationResponse> deletePromptGlobalDefect(String id) {
		return promptGlobalDefect.deleteById(id)
				.then(Mono.just(new PromptGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de Prompt global defect", e);
				});
	}

}
