package com.example.kafkaconsumer.repository;//package com.example.kafkaproducer.repository;

import kafka.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    School findByName(String name);

    boolean existsByName(String name);

}
