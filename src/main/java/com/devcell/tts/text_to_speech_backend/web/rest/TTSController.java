package com.devcell.tts.text_to_speech_backend.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

//import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcell.tts.text_to_speech_backend.domain.TTSRequest;
import com.devcell.tts.text_to_speech_backend.services.TTSService;



@RestController
@RequestMapping("/api/tts")
//@CrossOrigin(origins = "*")
public class TTSController {
	
	private static final Logger log = LoggerFactory.getLogger(TTSController.class);
    
	private final TTSService ttsService;
    
    public TTSController(TTSService ttsService) {
        this.ttsService = ttsService;
    }
    
    @PostMapping("/convert")
    public ResponseEntity<Resource> convertToSpeech(@RequestBody TTSRequest request) {
        try {
            
            log.debug(">> request {}", request);
            log.debug(">> request text: {}", request.getText());
            
            Resource audioResource = ttsService.convertTextToSpeech(request.getText());
            
            log.debug(">> audioResource length: {}", audioResource.contentLength());
//            log.debug(">> audioResource getContentAsString: {}", audioResource.getContentAsString(null));
                        
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"speech.mp3\"")
                .body(audioResource);
        } catch (Exception e) {
        	log.error("Exception: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}