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
import com.example.facturas_sinteticas_mongo.service.PublicityDataGenerationService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicityDataGenerationController {
	
	private final PublicityDataGenerationService publicityDataGenerationService;
	
	public PublicityDataGenerationController(@Qualifier("publicityService") PublicityDataGenerationService publicityDataGenerationService) {
		this.publicityDataGenerationService= publicityDataGenerationService;
	}
	
	@PostMapping("/save-publicity-data")
	public Mono<ResponseEntity<SyntheticDataGenerationRequest>> saveSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return publicityDataGenerationService.saveAndUpdatePublicityGeneration(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-publicity-data")
	public Mono<ResponseEntity<AllSyntheticDataGenerationResponse>> getAllSyntheticData(){
		return publicityDataGenerationService.getAllPublicityGeneration()
				.collectList()
				.map(list->{
					if(list.isEmpty()) {
						return ResponseEntity.ok(new AllSyntheticDataGenerationResponse());
					}else {
						var allSynthetic= new AllSyntheticDataGenerationResponse();
						allSynthetic.setSynthetics(list);
						return ResponseEntity.ok(allSynthetic);
					}
				});
	}
	
	@DeleteMapping("/delete-publicity-data")
	public Mono<ResponseEntity<SyntheticDataGenerationResponse>> deleteSyntheticData(@RequestBody SyntheticDataGenerationRequest request){
		return publicityDataGenerationService.deletePublicityData(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
