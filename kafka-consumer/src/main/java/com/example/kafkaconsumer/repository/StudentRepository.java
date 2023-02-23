package com.example.kafkaconsumer.repository;//package com.example.kafkaproducer.repository;

import kafka.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByStudentNo(int studentNO);

}
