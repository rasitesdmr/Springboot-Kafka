package com.example.kafkaconsumer.service;

import kafka.model.Student;
import kafka.model.StudentListResponse;
import kafka.model.StudentResponse;

import java.util.List;

public interface StudentService {

    void createStudent(StudentResponse studentResponse);

    List<StudentListResponse> getAllStudentList();
}
