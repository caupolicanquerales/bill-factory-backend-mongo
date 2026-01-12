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

import com.example.facturas_sinteticas_mongo.request.BasicTemplateRequest;
import com.example.facturas_sinteticas_mongo.request.PromptGenerationRequest;
import com.example.facturas_sinteticas_mongo.response.AllBasicTemplateResponse;
import com.example.facturas_sinteticas_mongo.response.BasicTemplateResponse;
import com.example.facturas_sinteticas_mongo.service.BasicTemplateBillService;

import reactor.core.publisher.Mono;
import com.example.facturas_sinteticas_mongo.service.utils.ResponseUtil;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "http://localhost:4200")
public class BasicTemplateBillController {
	
	private final BasicTemplateBillService basicTemplateBillService;
	
	public BasicTemplateBillController(@Qualifier("basicTemplateService") BasicTemplateBillService basicTemplateBillService) {
		this.basicTemplateBillService= basicTemplateBillService;
	}
	
	@PostMapping("/save-basic-template")
	public Mono<ResponseEntity<PromptGenerationRequest>> saveBasicTemplate(@RequestBody BasicTemplateRequest request){
		return basicTemplateBillService.saveAndUpdateBasicTemplate(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-basic-template")
	public Mono<ResponseEntity<AllBasicTemplateResponse>> getAllBasicTemplates(){
		return ResponseUtil.toListResponse(
				basicTemplateBillService.getAllBasicTemplate(),
				AllBasicTemplateResponse::new,
				AllBasicTemplateResponse::setBasicTemplates
		);
	}
	
	@DeleteMapping("/delete-basic-template")
	public Mono<ResponseEntity<BasicTemplateResponse>> deleteImperfection(@RequestBody BasicTemplateRequest request){
		return basicTemplateBillService.deleteBasicTemplate(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping("/get-basic-template")
	public Mono<ResponseEntity<BasicTemplateResponse>> getBasicTemplateById(@RequestBody BasicTemplateRequest request){
		return basicTemplateBillService.findBasicTemplate(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
}
