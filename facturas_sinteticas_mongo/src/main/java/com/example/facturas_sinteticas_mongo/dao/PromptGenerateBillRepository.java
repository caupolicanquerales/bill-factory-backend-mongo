package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.PromptGenerationBill;

import reactor.core.publisher.Mono;


@Repository("promptGenerateBillRepository")
public interface PromptGenerateBillRepository extends ReactiveMongoRepository<PromptGenerationBill, String>{
	Mono<PromptGenerationBill> save(PromptGenerationBill imperfection);
}
