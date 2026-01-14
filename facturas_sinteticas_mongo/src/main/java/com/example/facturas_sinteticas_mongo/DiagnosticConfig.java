package com.example.facturas_sinteticas_mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import org.springframework.context.annotation.Primary;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

@Configuration
public class DiagnosticConfig {

    private static final Logger log = LoggerFactory.getLogger(DiagnosticConfig.class);

    @Bean
    public static CommandLineRunner logMongoConfig(ConfigurableEnvironment env) {
        return args -> {
            String[] keys = new String[]{
                    "spring.data.mongodb.uri",
                    "spring.data.mongodb.host",
                    "spring.data.mongodb.port",
                    "spring.mongodb.uri",
                    "mongodb.uri",
                    "MONGO_INITDB_DATABASE"
            };
            for (String k : keys) {
                String vEnv = System.getenv(k.replace('.', '_').toUpperCase());
                String vProp = env.getProperty(k);
                String vSys = System.getProperty(k);
                log.info("Property check - {} -> env='{}' prop='{}' sys='{}'", k, vEnv, vProp, vSys);
            }
        };
    }

    @Bean
    public static BeanPostProcessor mongoClientBeanLogger() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                try {
                    boolean isMongoClient = bean instanceof com.mongodb.client.MongoClient || bean instanceof com.mongodb.reactivestreams.client.MongoClient;
                    if (isMongoClient) {
                        log.info("MongoClient bean created: name={} class={}", beanName, bean.getClass().getName());
                        try {
                            Method m = bean.getClass().getMethod("getSettings");
                            Object settings = m.invoke(bean);
                            log.info("MongoClient.getSettings() = {}", settings);
                        } catch (NoSuchMethodException e) {
                            log.info("MongoClient implementation has no getSettings() method: {}", bean.getClass().getName());
                        }
                    }
                } catch (Throwable t) {
                    log.warn("Error inspecting bean {}: {}", beanName, t.toString());
                }
                return bean;
            }
        };
    }

    @Bean
    @Primary
    public static com.mongodb.client.MongoClient primaryMongoClient(ConfigurableEnvironment env) {
        String uri = env.getProperty("spring.data.mongodb.uri");
        ConnectionString cs = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(cs).build();
        return com.mongodb.client.MongoClients.create(settings);
    }

    @Bean
    @Primary
    public static com.mongodb.reactivestreams.client.MongoClient primaryReactiveMongoClient(ConfigurableEnvironment env) {
        String uri = env.getProperty("spring.data.mongodb.uri");
        ConnectionString cs = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(cs).build();
        return com.mongodb.reactivestreams.client.MongoClients.create(settings);
    }
}
