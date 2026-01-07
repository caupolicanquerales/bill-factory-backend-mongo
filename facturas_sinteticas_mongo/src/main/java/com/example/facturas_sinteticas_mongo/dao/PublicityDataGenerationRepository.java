package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.PublicityDataGeneration;

import reactor.core.publisher.Mono;


@Repository("publicityDataGenerationRepository")
public interface PublicityDataGenerationRepository extends ReactiveMongoRepository<PublicityDataGeneration, String>{
	Mono<PublicityDataGeneration> save(PublicityDataGeneration imperfection);
}
