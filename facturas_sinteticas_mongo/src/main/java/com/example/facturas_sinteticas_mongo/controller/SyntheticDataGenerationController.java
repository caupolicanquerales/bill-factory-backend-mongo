package com.example.facturas_sinteticas_mongo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.facturas_sinteticas_mongo.request.SyntheticDataGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.AllSyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.SyntheticDataGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.SyntheticDataGenerationService;

import reactor.core.publisher.Mono;
import com.example.facturas_sinteticas_mongo.service.utils.ResponseUtil;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "${app.frontend.url}")
public class SyntheticDataGenerationController {
	
	private final SyntheticDataGenerationService syntheticDataGenerationService;
	
	public SyntheticDataGenerationController(@Qualifier("syntheticService") SyntheticDataGenerationService syntheticDataGenerationService) {
		this.syntheticDataGenerationService= syntheticDataGenerationService;
	}
	
	@PostMapping("/save-synthetic-data")
	public Mono<ResponseEntity<SyntheticDataGenerationRequest>> saveSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return syntheticDataGenerationService.saveAndUpdateSyntheticGeneration(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-synthetic-data")
	public Mono<ResponseEntity<AllSyntheticDataGenerationResponse>> getAllSyntheticData(){
		return ResponseUtil.toListResponse(
				syntheticDataGenerationService.getAllSyntheticGeneration(),
				AllSyntheticDataGenerationResponse::new,
				AllSyntheticDataGenerationResponse::setSynthetics
		);
	}
	
	@DeleteMapping("/delete-synthetic-data")
	public Mono<ResponseEntity<SyntheticDataGenerationResponse>> deleteSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return syntheticDataGenerationService.deleteSyntheticData(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
