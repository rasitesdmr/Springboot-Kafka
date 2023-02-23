package com.example.kafkaproducer.Controller;

import com.example.kafkaproducer.service.StudentService;
import kafka.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(value = "/createStudent", consumes = "multipart/form-data")
    ResponseEntity<Student> createStudent(@RequestPart("student") Student student, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(studentService.createStudent(student, multipartFile), HttpStatus.CREATED);
    }
}


