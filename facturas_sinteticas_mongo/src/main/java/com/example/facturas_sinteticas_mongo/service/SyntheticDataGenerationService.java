package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.SyntheticDataGenerationRepository;
import com.example.facturas_sinteticas_mongo.model.SyntheticDataGeneration;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("syntheticService")
public class SyntheticDataGenerationService {
	
	private static final Logger LOG = LogManager.getLogger(SyntheticDataGenerationService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final SyntheticDataGenerationRepository syntheticDataGeneration;
	
	public SyntheticDataGenerationService(@Qualifier("syntheticDataGenerationRepository") SyntheticDataGenerationRepository syntheticDataGeneration) {
		this.syntheticDataGeneration= syntheticDataGeneration;
	}
	
	public Mono<SyntheticDataGenerationRequest> saveAndUpdateSyntheticGeneration(SyntheticDataGenerationRequest request) {
		SyntheticDataGeneration syntheticGeneration= mapper.convertValue(request, SyntheticDataGeneration.class);
		return syntheticDataGeneration.save(syntheticGeneration)
				.map(response->{
					var syntheticData= new SyntheticDataGenerationRequest();
					syntheticData.setId(response.getId());
					return syntheticData;
				})
				.doOnNext(item->{
					LOG.info("Prompt generation images guardado: "+ item.getName());
				})
				.doOnError(e->{
					LOG.error("Error al guardar inperfection", e);
				});
	}
	
	
	public Flux<SyntheticDataGenerationResponse> getAllSyntheticGeneration() {
		return syntheticDataGeneration.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, SyntheticDataGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	public Mono<SyntheticDataGenerationResponse> deleteSyntheticData(String id) {
		return syntheticDataGeneration.deleteById(id)
				.then(Mono.just(new SyntheticDataGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}

}
