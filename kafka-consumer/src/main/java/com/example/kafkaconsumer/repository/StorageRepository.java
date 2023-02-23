package com.example.kafkaconsumer.repository;//package com.example.kafkaproducer.repository;

import kafka.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StorageRepository extends JpaRepository<ImageData,Long> {

    ImageData findByName(String name);

    boolean existsByName(String name);

}