package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.GlobalDefect;

import reactor.core.publisher.Mono;


@Repository("globalDefectMongoRepository")
public interface GlobalDefectMongoRepository extends ReactiveMongoRepository<GlobalDefect, String>{
	Mono<GlobalDefect> save(GlobalDefect imperfection);
}
