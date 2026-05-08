package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.ImageVariablesDataGenerationRepository;
import com.example.facturas_sinteticas_mongo.model.ImageVariablesDataGeneration;
import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;

import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("imageVariablesService")
public class ImageVariablesGenerationService {
	
	private static final Logger LOG = LogManager.getLogger(ImageVariablesGenerationService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final ImageVariablesDataGenerationRepository imageVariablesData;
	
	public ImageVariablesGenerationService(@Qualifier("imageVariablesDataGenerationRepository") ImageVariablesDataGenerationRepository imageVariablesData) {
		this.imageVariablesData= imageVariablesData;
	}
	
	public Mono<SyntheticDataGenerationRequest> saveAndUpdateImageVariables(SyntheticDataGenerationRequest request) {
		ImageVariablesDataGeneration imageVariables= mapper.convertValue(request, ImageVariablesDataGeneration.class);
		return imageVariablesData.save(imageVariables)
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
	
	
	public Mono<SyntheticDataGenerationResponse> deleteImageVaribleData(String id) {
		return imageVariablesData.deleteById(id)
				.then(Mono.just(new SyntheticDataGenerationResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de variables de imagenes", e);
				});
	}
	
	public Mono<SyntheticDataGenerationResponse> findImageVaribleData(String id) {
		return imageVariablesData.findById(id)
				.map(imageVariables-> mapper.convertValue(imageVariables, SyntheticDataGenerationResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de variables de imagenes", e);
				});
	}

}
