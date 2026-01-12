package com.example.facturas_sinteticas_mongo.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.example.facturas_sinteticas_mongo.model.PromptGlobalDefect;

@Repository("promptGlobalDefectRepository")
public interface PromptGlobalDefectRepository extends ReactiveMongoRepository<PromptGlobalDefect, String>{
}
