package com.example.kafkaproducer.service;

import kafka.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {

    Student createStudent(Student student, MultipartFile multipartFile) throws IOException;


}
