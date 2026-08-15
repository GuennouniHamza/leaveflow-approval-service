package com.leaveflow.approval_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

@Configuration
public class KafkaProducerConfig {
	
	//l'adresse du serveur Kafka (broker) auquel 
	//ton application Spring veut se connecter.
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    
    
    //demande a spring de creer la factrory qui gérent les producers KAFKA used for message sent .
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        config.put(JacksonJsonSerializer.TYPE_MAPPINGS,
                "approvalDecision:com.leaveflow.approval_service.event.ApprovalDecisionEvent");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    /*ProducerFactory configure la création des Kafka Producers, 
     * notamment l'adresse de Kafka et les serializers. 
     * KafkaTemplate est ensuite utilisé par l'application pour envoyer les messages. 
     * Ici, les clés sont sérialisées en String et les objets Java sont sérialisés en JSON grâce à JsonSerializer.
     Et le point important : @Bean rend ProducerFactory et KafkaTemplate disponibles dans le conteneur Spring pour pouvoir les injecter ailleurs.*/
}