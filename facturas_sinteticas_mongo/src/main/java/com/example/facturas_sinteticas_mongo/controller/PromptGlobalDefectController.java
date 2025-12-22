package com.example.facturas_sinteticas_mongo.controller;

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
import com.example.facturas_sinteticas_mongo.service.PromptGenerationBillService;
import com.example.facturas_sinteticas_mongo.service.PromptGlobalDefectService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "http://localhost:4200")
public class PromptGlobalDefectController {
	
	private final PromptGlobalDefectService promptGlobalDefectService;
	
	public PromptGlobalDefectController( PromptGlobalDefectService promptGlobalDefectService) {
		this.promptGlobalDefectService= promptGlobalDefectService;
	}
	
	@PostMapping("/save-global-defect-prompt")
	public Mono<ResponseEntity<PromptGenerationRequest>> saveImage(@RequestBody PromptGenerationRequest promptGeneration){
		return promptGlobalDefectService.saveAndUpdatePromptGlobalDefect(promptGeneration)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-global-defect-prompt")
	public Mono<ResponseEntity<AllPromptGenerationResponse>> getAllPromptGlobalDefect(){
		return promptGlobalDefectService.getAllPromptGlobalDefect()
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
	
	
	@DeleteMapping("/delete-global-defect-prompt")
	public Mono<ResponseEntity<PromptGenerationResponse>> deletePromptGlobalDefect(@RequestBody PromptGenerationRequest promptGenerationImage){
		return promptGlobalDefectService.deletePromptGlobalDefect(promptGenerationImage.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
