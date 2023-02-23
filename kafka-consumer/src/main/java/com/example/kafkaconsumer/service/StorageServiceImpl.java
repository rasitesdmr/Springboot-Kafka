package com.example.kafkaconsumer.service;

import com.example.kafkaconsumer.repository.StorageRepository;
import com.example.kafkaconsumer.repository.StudentRepository;
import kafka.model.ImageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;

    @Override
    public void createImage(ImageData imageData) {

        if (!storageRepository.existsByName(imageData.getName())) {
            storageRepository.save(imageData);
            log.info("image database kaydedildi : " + imageData.getName());
        }

    }
}
