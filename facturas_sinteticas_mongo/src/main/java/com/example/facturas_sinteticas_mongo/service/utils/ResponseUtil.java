package com.example.facturas_sinteticas_mongo.service.utils;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ResponseUtil {

    public static <T, R> Mono<ResponseEntity<R>> toListResponse(
            Flux<T> flux,
            Supplier<R> wrapperSupplier,
            BiConsumer<R, List<T>> setter) {
        return flux
                .collectList()
                .map(list -> {
                    R wrapper = wrapperSupplier.get();
                    if (!list.isEmpty()) {
                        setter.accept(wrapper, list);
                    }
                    return ResponseEntity.ok(wrapper);
                });
    }
}
