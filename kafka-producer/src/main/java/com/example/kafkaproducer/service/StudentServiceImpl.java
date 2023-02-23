package com.example.kafkaproducer.service;

import com.example.kafkaproducer.producer.KafkaProducer;

import com.example.kafkaproducer.util.ImageUtils;
import kafka.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final KafkaProducer kafkaProducer;

    @Override
    public Student createStudent(Student student, MultipartFile multipartFile) throws IOException {
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setEmail(student.getEmail());
        newStudent.setStudentNo(student.getStudentNo());

        School school = new School();
        school.setName(student.getSchool().getName());

        newStudent.setSchool(school);
        kafkaProducer.schoolProducer(school);

        log.info("Student sınıfı kuyruğa gönderildi : " + school.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ImageData imageData = new ImageData();
        imageData.setName(multipartFile.getOriginalFilename());
        imageData.setType(multipartFile.getContentType());
        imageData.setImageData(ImageUtils.compressImage(multipartFile.getBytes()));

        newStudent.setImageName(multipartFile.getOriginalFilename());
        log.info("Dosya Başarıyla yüklendi :" + multipartFile.getOriginalFilename());

        kafkaProducer.imageProducer(imageData);
        log.info("ImageDate sınıfı kuyruğa gönderildi : " + imageData.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setFirstName(newStudent.getFirstName());
        studentResponse.setLastName(newStudent.getLastName());
        studentResponse.setEmail(newStudent.getEmail());
        studentResponse.setStudentNo(newStudent.getStudentNo());
        studentResponse.setSchoolName(newStudent.getSchool().getName());
        studentResponse.setImageName(newStudent.getImageName());

        kafkaProducer.studentProducer(studentResponse);
        log.info("Student sınıfı kuyruğa gönderildi : " + student.toString());

        return newStudent;
    }



}
