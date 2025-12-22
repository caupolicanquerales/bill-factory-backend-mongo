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

import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.AllPromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.response.PromptGenerationResponse;
import com.example.facturas_sinteticas_mongo.service.PromptGenerationDataService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "http://localhost:4200")
public class PromptGenerationDataController {
	
	private final PromptGenerationDataService promptGenerationDataService;
	
	public PromptGenerationDataController(@Qualifier("dataService") PromptGenerationDataService promptGenerationDataService) {
		this.promptGenerationDataService= promptGenerationDataService;
	}
	
	@PostMapping("/save-data-prompt")
	public Mono<ResponseEntity<PromptGenerationRequest>> saveImage(@RequestBody PromptGenerationRequest promptGeneration){
		return promptGenerationDataService.saveAndUpdatePromptGeneration(promptGeneration)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-data-prompt")
	public Mono<ResponseEntity<AllPromptGenerationResponse>> getAllDataPrompts(){
		return promptGenerationDataService.getAllPromptGeneration()
				.collectList()
				.map(list->{
					if(list.isEmpty()) {
						return ResponseEntity.ok(new AllPromptGenerationResponse());
					}else {
						var allPrompt= new AllPromptGenerationResponse();
						allPrompt.setPrompts(list);
						return ResponseEntity.ok(allPrompt);
					}
				});
	}
	
	@DeleteMapping("/delete-data-prompt")
	public Mono<ResponseEntity<PromptGenerationResponse>> deleteDataPrompt(@RequestBody PromptGenerationRequest promptGeneration){
		return promptGenerationDataService.deletePrompt(promptGeneration.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
