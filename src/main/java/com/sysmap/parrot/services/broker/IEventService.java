package com.sysmap.parrot.services.broker;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IEventService {
    void send (String event);
    void consume(ConsumerRecord<String, String> event);
}
