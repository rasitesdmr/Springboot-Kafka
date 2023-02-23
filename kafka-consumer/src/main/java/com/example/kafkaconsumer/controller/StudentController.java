package com.example.kafkaconsumer.controller;

import com.example.kafkaconsumer.repository.StudentRepository;
import com.example.kafkaconsumer.service.StudentService;
import kafka.model.Student;
import kafka.model.StudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/list")
    ResponseEntity<List<StudentListResponse>> getStudentList() {
        return new ResponseEntity<>(studentService.getAllStudentList(), HttpStatus.OK);
    }

}
