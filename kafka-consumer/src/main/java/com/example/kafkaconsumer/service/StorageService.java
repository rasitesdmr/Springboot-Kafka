package com.example.kafkaconsumer.service;

import kafka.model.ImageData;

public interface StorageService {

    void createImage(ImageData imageData);
}
