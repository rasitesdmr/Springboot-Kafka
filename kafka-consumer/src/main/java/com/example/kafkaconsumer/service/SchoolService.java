package com.example.kafkaconsumer.service;

import kafka.model.School;

public interface SchoolService {

    void createSchool(School school);
}
