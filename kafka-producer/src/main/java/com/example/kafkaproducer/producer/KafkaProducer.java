package com.example.kafkaproducer.producer;

import kafka.model.ImageData;
import kafka.model.School;
import kafka.model.Student;
import kafka.model.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void studentProducer(StudentResponse studentResponse) {
        kafkaTemplate.send("student-topic", 0, "key-1", studentResponse);
    }

    public void schoolProducer(School school) {
        kafkaTemplate.send("student-topic", 1, "key-2", school);
    }

    public void imageProducer(ImageData imageData) {
        kafkaTemplate.send("student-topic", 2, "key-3", imageData);
    }




}
