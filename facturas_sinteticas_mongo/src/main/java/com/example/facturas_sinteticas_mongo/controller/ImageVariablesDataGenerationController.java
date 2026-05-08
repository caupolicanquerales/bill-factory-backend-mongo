package com.example.facturas_sinteticas_mongo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.ImageVariablesGenerationService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "${app.frontend.url}")
public class ImageVariablesDataGenerationController {
	
	private final ImageVariablesGenerationService imageVariablesGenerationService;
	
	public ImageVariablesDataGenerationController(@Qualifier("imageVariablesService") ImageVariablesGenerationService imageVariablesGenerationService) {
		this.imageVariablesGenerationService= imageVariablesGenerationService;
	}
	
	@PostMapping("/save-image-variables-data")
	public Mono<ResponseEntity<SyntheticDataGenerationRequest>> saveSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return imageVariablesGenerationService.saveAndUpdateImageVariables(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@DeleteMapping("/delete-image-variables-data")
	public Mono<ResponseEntity<SyntheticDataGenerationResponse>> deleteSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return imageVariablesGenerationService.deleteImageVaribleData(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/get-image-variables-data")
	public Mono<ResponseEntity<SyntheticDataGenerationResponse>> getImageVariablesDataById(@RequestBody SyntheticDataGenerationRequest request){
		return imageVariablesGenerationService.findImageVaribleData(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
