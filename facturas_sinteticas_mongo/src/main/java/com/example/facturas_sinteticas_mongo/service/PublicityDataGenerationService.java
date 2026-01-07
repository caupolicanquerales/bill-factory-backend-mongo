package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.PublicityDataGenerationRepository;
import com.example.facturas_sinteticas_mongo.model.PublicityDataGeneration;
import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("publicityService")
public class PublicityDataGenerationService {
	
	private static final Logger LOG = LogManager.getLogger(PublicityDataGenerationService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final PublicityDataGenerationRepository publicityDataGeneration;
	
	public PublicityDataGenerationService(@Qualifier("publicityDataGenerationRepository") PublicityDataGenerationRepository publicityDataGeneration) {
		this.publicityDataGeneration= publicityDataGeneration;
	}
	
	public Mono<SyntheticDataGenerationRequest> saveAndUpdatePublicityGeneration(SyntheticDataGenerationRequest request) {
		PublicityDataGeneration syntheticGeneration= mapper.convertValue(request, PublicityDataGeneration.class);
		return publicityDataGeneration.save(syntheticGeneration)
				.map(response->{
					var syntheticData= new SyntheticDataGenerationRequest();
					syntheticData.setId(response.getId());
					return syntheticData;
				})
				.doOnNext(item->{
					LOG.info("Datos de publicidad guardado: "+ item.getName());
				})
				.doOnError(e->{
					LOG.error("Error al guardar Datos de publicidad", e);
				});
	}
	
	
	public Flux<SyntheticDataGenerationResponse> getAllPublicityGeneration() {
		return publicityDataGeneration.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, SyntheticDataGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de datos de publicidad", e);
				});
	}
	
	
	public Mono<SyntheticDataGenerationResponse> deletePublicityData(String id) {
		return publicityDataGeneration.deleteById(id)
				.then(Mono.just(new SyntheticDataGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de datos de publicidad", e);
				});
	}

}
