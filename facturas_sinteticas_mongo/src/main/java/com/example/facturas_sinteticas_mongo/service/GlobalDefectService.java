package com.example.facturas_sinteticas_mongo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.facturas_sinteticas_mongo.dao.GlobalDefectMongoRepository;
import com.example.facturas_sinteticas_mongo.model.GlobalDefect;
import com.example.facturas_sinteticas_mongo.request.GlobalDefectRequest;
import com.example.facturas_sinteticas_mongo.response.GlobalDefectResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Service("globalDefect")
public class GlobalDefectService {
	
	private static final Logger LOG = LogManager.getLogger(GlobalDefectService.class); 
	private ObjectMapper mapper = new ObjectMapper();
	private final GlobalDefectMongoRepository globalDefect;
	
	public GlobalDefectService(@Qualifier("globalDefectMongoRepository") GlobalDefectMongoRepository globalDefect) {
		this.globalDefect= globalDefect;
	}
	
	public Mono<GlobalDefect> saveAndUpdateGlobalDefect(GlobalDefectRequest imperfection) {
		GlobalDefect visuaImperfection= mapper.convertValue(imperfection, GlobalDefect.class);
		return globalDefect.save(visuaImperfection)
				.doOnNext(item->{
					LOG.info("Defecto visual guardado: "+ item.getDefect());
				})
				.doOnError(e->{
					LOG.error("Error al guardar inperfection", e);
				});
	}

	public Flux<GlobalDefectResponse> getAllGlobalDefect() {
		return globalDefect.findAll()
				.map(promptImage-> mapper.convertValue(promptImage, GlobalDefectResponse.class))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	public Mono<GlobalDefectResponse> deleteGlobalDefect(String id) {
		return globalDefect.deleteById(id)
				.then(Mono.just(new GlobalDefectResponse()))
				.doOnError(e->{
					LOG.error("Error en la obtencion del objeto de imperfection", e);
				});
	}
	
	
	
}
