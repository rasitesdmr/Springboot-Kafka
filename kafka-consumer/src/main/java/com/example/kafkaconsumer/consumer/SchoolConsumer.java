package com.example.kafkaconsumer.consumer;

import com.example.kafkaconsumer.service.SchoolService;
import com.example.kafkaconsumer.service.StorageService;
import com.example.kafkaconsumer.service.StudentService;
import kafka.model.ImageData;
import kafka.model.School;
import kafka.model.StudentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class SchoolConsumer {

    private final SchoolService schoolService;
    private final StorageService storageService;

    private final StudentService studentService;

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "student-topic",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void schoolConsumer(School school) {
        log.info("kuyruktan gelen school bilgileri :" + school);
        schoolService.createSchool(school);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "student-topic",
                    partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void imageConsumer(ImageData imageData) {
        log.info("kuyruktan gelen imageData bilgileri :" + imageData.toString());
        storageService.createImage(imageData);
    }

    @KafkaListener(groupId = "kafka-group-id",
            topicPartitions = {@TopicPartition(topic = "student-topic",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0", relativeToCurrent = "true"))}
    )
    public void studentConsumer(StudentResponse studentResponse) {
        studentService.createStudent(studentResponse);
    }



}
