package com.devcell.tts.text_to_speech_backend.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devcell.tts.text_to_speech_backend.domain.TTSEntity;
import com.devcell.tts.text_to_speech_backend.repository.TTSRepository;

//import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TTSService {
    
    @Value("${python.tts.service.url}")
    private String pythonServiceUrl;
    
    //private final RestTemplate restTemplate;
    //private final TTSRepository ttsRepository;
    
//    public TTSService(RestTemplate restTemplate, TTSRepository ttsRepository) {
//        this.restTemplate = restTemplate;
//        this.ttsRepository = ttsRepository;
//    }
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private TTSRepository ttsRepository;
    
    public Resource convertTextToSpeech(String text) {
        // 1. Save request to database
        TTSEntity ttsEntity = new TTSEntity();
        ttsEntity.setText(text);
        ttsEntity.setRequestTime(LocalDateTime.now());
        ttsRepository.save(ttsEntity);
        
        // 2. Call Python TTS service
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", text);
        
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<byte[]> response = restTemplate.postForEntity(
            pythonServiceUrl + "/tts",
            request,
            byte[].class
        );
        
        // 3. Update database with response
        ttsEntity.setCompletionTime(LocalDateTime.now());
        ttsEntity.setStatus("COMPLETED");
        ttsRepository.save(ttsEntity);
        
        // 4. Return audio resource
        ByteArrayResource resource = new ByteArrayResource(response.getBody());
        return resource;
    }
}