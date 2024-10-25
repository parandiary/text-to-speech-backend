package com.devcell.tts.text_to_speech_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TextToSpeechBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextToSpeechBackendApplication.class, args);
	}

}
