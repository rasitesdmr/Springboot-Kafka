package com.example.kafkaconsumer.service;

import com.example.kafkaconsumer.repository.SchoolRepository;
import kafka.model.School;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public void createSchool(School school) {

        if (!schoolRepository.existsByName(school.getName())) {
            schoolRepository.save(school);
            log.info("school database kaydedildi : " + school);
        }
        
    }
}
