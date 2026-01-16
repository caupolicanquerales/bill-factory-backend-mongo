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
import com.example.facturas_sinteticas_mongo.service.PromptGenerationSystemService;

import reactor.core.publisher.Mono;
import com.example.facturas_sinteticas_mongo.service.utils.ResponseUtil;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "${app.frontend.url}")
public class PromptGenerationSystemController {
	
	private final PromptGenerationSystemService promptGenerationSystemService;
	
	public PromptGenerationSystemController(@Qualifier("systemService") PromptGenerationSystemService promptGenerationSystemService) {
		this.promptGenerationSystemService= promptGenerationSystemService;
	}
	
	@PostMapping("/save-system-prompt")
	public Mono<ResponseEntity<PromptGenerationRequest>> saveImage(@RequestBody PromptGenerationRequest promptGeneration){
		return promptGenerationSystemService.saveAndUpdatePromptGeneration(promptGeneration)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-system-prompt")
	public Mono<ResponseEntity<AllPromptGenerationResponse>> getAllImperfections(){
		return ResponseUtil.toListResponse(
				promptGenerationSystemService.getAllPromptGeneration(),
				AllPromptGenerationResponse::new,
				AllPromptGenerationResponse::setPrompts
		);
	}
	
	@DeleteMapping("/delete-system-prompt")
	public Mono<ResponseEntity<PromptGenerationResponse>> deleteImperfection(@RequestBody PromptGenerationRequest promptGenerationImage){
		return promptGenerationSystemService.deletePrompt(promptGenerationImage.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
