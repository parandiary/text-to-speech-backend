package com.devcell.tts.text_to_speech_backend.domain;

import java.time.LocalDateTime;
//import org.springframework.data.annotation.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tts_requests")
@Data
@NoArgsConstructor
public class TTSEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(columnDefinition = "TEXT")
    private String text;
    
    private LocalDateTime requestTime;
    private LocalDateTime completionTime;
    private String status;
}