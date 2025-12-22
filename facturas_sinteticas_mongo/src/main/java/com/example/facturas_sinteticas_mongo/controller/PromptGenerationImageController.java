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
import com.example.facturas_sinteticas_mongo.service.PromptGenerationImageService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "http://localhost:4200")
public class PromptGenerationImageController {
	
	private final PromptGenerationImageService promptGenerationImageService;
	
	public PromptGenerationImageController(@Qualifier("imageService") PromptGenerationImageService promptGenerationImageService) {
		this.promptGenerationImageService= promptGenerationImageService;
	}
	
	@PostMapping("/save-image-prompt")
	public Mono<ResponseEntity<PromptGenerationRequest>> saveImage(@RequestBody PromptGenerationRequest promptGenerationImage){
		return promptGenerationImageService.saveAndUpdatePromptGeneration(promptGenerationImage)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-image-prompt")
	public Mono<ResponseEntity<AllPromptGenerationResponse>> getAllPrompts(){
		return promptGenerationImageService.getAllPromptGeneration()
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
	
	
	@DeleteMapping("/delete-image-prompt")
	public Mono<ResponseEntity<PromptGenerationResponse>> deletePrompt(@RequestBody PromptGenerationRequest promptGenerationImage){
		return promptGenerationImageService.deletePrompt(promptGenerationImage.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
