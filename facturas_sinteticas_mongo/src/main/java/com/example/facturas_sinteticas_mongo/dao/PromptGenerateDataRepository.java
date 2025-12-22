package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.PromptGenerationData;

import reactor.core.publisher.Mono;


@Repository("promptGenerateDataRepository")
public interface PromptGenerateDataRepository extends ReactiveMongoRepository<PromptGenerationData, String>{
	Mono<PromptGenerationData> save(PromptGenerationData imperfection);
}
