package com.devcell.tts.text_to_speech_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devcell.tts.text_to_speech_backend.domain.TTSEntity;

@Repository
public interface TTSRepository extends JpaRepository<TTSEntity, Long> {
}