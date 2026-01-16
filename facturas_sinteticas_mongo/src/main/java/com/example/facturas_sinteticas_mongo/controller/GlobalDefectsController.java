package com.example.facturas_sinteticas_mongo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.facturas_sinteticas_mongo.model.GlobalDefect;
import com.example.facturas_sinteticas_mongo.request.GlobalDefectRequest;
import com.example.facturas_sinteticas_mongo.response.AllGlobalDefectResponse;
import com.example.facturas_sinteticas_mongo.response.GlobalDefectResponse;
import com.example.facturas_sinteticas_mongo.service.GlobalDefectService;

import reactor.core.publisher.Mono;
import com.example.facturas_sinteticas_mongo.service.utils.ResponseUtil;

@RestController
@RequestMapping("mongo")
@CrossOrigin(origins = "${app.frontend.url}")
public class GlobalDefectsController {
	
	private final GlobalDefectService globalDefectService;
	
	public GlobalDefectsController(GlobalDefectService globalDefectService) {
		this.globalDefectService= globalDefectService;
	}
	
	@PostMapping("/save-global-defect")
	public Mono<ResponseEntity<GlobalDefect>> saveImage(@RequestBody GlobalDefectRequest request){
		return globalDefectService.saveAndUpdateGlobalDefect(request)
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	
	@GetMapping("/all-global-defect")
	public Mono<ResponseEntity<AllGlobalDefectResponse>> getAllGlobalDefect(){
		return ResponseUtil.toListResponse(
				globalDefectService.getAllGlobalDefect(),
				AllGlobalDefectResponse::new,
				AllGlobalDefectResponse::setDefects
		);
	}
	
	@DeleteMapping("/delete-global-defect")
	public Mono<ResponseEntity<GlobalDefectResponse>> deleteGlobalDefect(@RequestBody GlobalDefectRequest request){
		return globalDefectService.deleteGlobalDefect(request.getId())
				.map(ResponseEntity.ok()::body)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
}
