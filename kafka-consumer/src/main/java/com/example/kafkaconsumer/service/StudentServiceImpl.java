package com.example.kafkaconsumer.service;

import com.example.kafkaconsumer.repository.StorageRepository;
import com.example.kafkaconsumer.repository.StudentRepository;
import com.example.kafkaconsumer.repository.SchoolRepository;
import kafka.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final SchoolRepository schoolRepository;
    private final StorageRepository storageRepository;
    private final StudentRepository studentRepository;


    @Override
    public void createStudent(StudentResponse studentResponse) {


        Student newStudent = new Student();
        newStudent.setFirstName(studentResponse.getFirstName());
        newStudent.setLastName(studentResponse.getLastName());
        newStudent.setEmail(studentResponse.getEmail());
        newStudent.setStudentNo(studentResponse.getStudentNo());
        newStudent.setSchool(schoolRepository.findByName(studentResponse.getSchoolName()));
        newStudent.setImageData(storageRepository.findByName(studentResponse.getImageName()));
        studentRepository.save(newStudent);

        log.info("student database kaydedildi : " + newStudent.toString());

    }

    @Override
    public List<StudentListResponse> getAllStudentList() {
        List<Student> student = studentRepository.findAll();
        List<StudentListResponse> studentListResponses = new ArrayList<>();

        for (Student studentNew: student){
            StudentListResponse studentListResponse = new StudentListResponse();
            studentListResponse.setId(studentNew.getId());
            studentListResponse.setFirstName(studentNew.getFirstName());
            studentListResponse.setLastName(studentNew.getLastName());
            studentListResponse.setEmail(studentNew.getEmail());
            studentListResponse.setStudentNo(studentNew.getStudentNo());

            SchoolResponse schoolResponse = new SchoolResponse();
            schoolResponse.setId(studentNew.getSchool().getId());
            schoolResponse.setName(studentNew.getSchool().getName());

            studentListResponse.setSchoolResponse(schoolResponse);

            ImageDateResponse imageDateResponse = new ImageDateResponse();
            imageDateResponse.setId(studentNew.getImageData().getId());
            imageDateResponse.setName(studentNew.getImageData().getName());
            imageDateResponse.setType(studentNew.getImageData().getType());

            studentListResponse.setImageDateResponse(imageDateResponse);
            studentListResponses.add(studentListResponse);
        }
        return studentListResponses;
    }


}
