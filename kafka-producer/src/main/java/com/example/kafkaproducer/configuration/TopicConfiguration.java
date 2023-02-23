package com.example.kafkaproducer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {

    @Bean
    public NewTopic createStudentTopic() {
        return TopicBuilder
                .name("student-topic")
                .partitions(3)
                .build();
    }



}
