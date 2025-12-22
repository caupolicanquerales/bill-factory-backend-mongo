package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.BasicTemplateBill;

import reactor.core.publisher.Mono;


@Repository("basicTemplateBillRepository")
public interface BasicTemplateBillRepository extends ReactiveMongoRepository<BasicTemplateBill, String>{
	Mono<BasicTemplateBill> save(BasicTemplateBill basicTemplate);
}
