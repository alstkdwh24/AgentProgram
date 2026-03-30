package com.agentprogram.service;

import com.agentprogram.dto.GptRequest;
import com.agentprogram.dto.GptResponse;
import com.agentprogram.entity.Gpt;
import com.agentprogram.repository.GptRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GptService {

    private final GptRepository gptRepository;

    public List<GptResponse> findAll() {
        return gptRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public GptResponse findById(Long id) {
        Gpt gpt = gptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GPT를 찾을 수 없습니다. id: " + id));
        return toResponse(gpt);
    }

    @Transactional
    public GptResponse save(GptRequest request) {
        Gpt gpt = Gpt.builder()
                .name(request.getName())
                .model(request.getModel())
                .description(request.getDescription())
                .build();
        return toResponse(gptRepository.save(gpt));
    }

    @Transactional
    public GptResponse update(Long id, GptRequest request) {
        Gpt gpt = gptRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GPT를 찾을 수 없습니다. id: " + id));
        gpt.setName(request.getName());
        gpt.setModel(request.getModel());
        gpt.setDescription(request.getDescription());
        return toResponse(gpt);
    }

    @Transactional
    public void delete(Long id) {
        if (!gptRepository.existsById(id)) {
            throw new EntityNotFoundException("GPT를 찾을 수 없습니다. id: " + id);
        }
        gptRepository.deleteById(id);
    }

    private GptResponse toResponse(Gpt gpt) {
        return GptResponse.builder()
                .id(gpt.getId())
                .name(gpt.getName())
                .model(gpt.getModel())
                .description(gpt.getDescription())
                .createdAt(gpt.getCreatedAt())
                .updatedAt(gpt.getUpdatedAt())
                .build();
    }
}
