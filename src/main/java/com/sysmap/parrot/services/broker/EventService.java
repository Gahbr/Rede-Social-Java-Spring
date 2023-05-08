package com.sysmap.parrot.services.broker;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventService implements IEventService{

    private KafkaTemplate<String, String> _kafka;

    @Value("${topic.name}")
    private String topic;

    @Autowired
    public EventService(KafkaTemplate<String, String> kafka, @Value("${topic.name}") String topic) {
        this._kafka = kafka;
        this.topic = topic;
    }

    public void send (String event){
        _kafka.send(topic, event);
    }

    @KafkaListener(topics = "${topic.name}" , groupId = "ms-demo")
    public void consume(ConsumerRecord<String, String> event){
        System.out.println("NOSSO EVENTO -> " + event.value());
    }
}

