package com.leaveflow.approval_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

@Configuration
@EnableKafka
/*@EnableKafka Active la prise en charge des mécanismes Kafka de Spring, notamment les @KafkaListener.*/
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "approval-service-group");
        /*Le group.id identifie le Consumer Group auquel appartient ton Consumer.*/
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);
        config.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(JacksonJsonDeserializer.TYPE_MAPPINGS,
                "leaveRequested:com.leaveflow.approval_service.event.LeaveRequestedEvent");
        return new DefaultKafkaConsumerFactory<>(config);
    }
    /* btw deserializer json to object && serializer object to json */

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
/*ConsumerFactory configure les Consumers Kafka, notamment le broker, 
 * le consumer group et les déserializers. 
 * ConcurrentKafkaListenerContainerFactory permet à Spring de gérer les containers 
 * qui exécutent les méthodes annotées avec @KafkaListener. 
 * Le JsonDeserializer transforme ensuite le message JSON reçu depuis Kafka en objet Java.*/


/*!!!!! important
 * PRODUCER
Objet Java
    ↓
JsonSerializer
    ↓
JSON / bytes
    ↓
Kafka
    ↓
Consumer
    ↓
JsonDeserializer
    ↓
Objet Java */
