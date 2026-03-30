package com.agentprogram.service;

import com.agentprogram.dto.AgentProgramRequest;
import com.agentprogram.dto.AgentProgramResponse;
import com.agentprogram.entity.AgentProgram;
import com.agentprogram.entity.AgentStatus;
import com.agentprogram.entity.Gpt;
import com.agentprogram.repository.AgentProgramRepository;
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
public class AgentProgramService {

    private final AgentProgramRepository agentProgramRepository;
    private final GptRepository gptRepository;

    public List<AgentProgramResponse> findAll() {
        return agentProgramRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AgentProgramResponse findById(Long id) {
        AgentProgram agentProgram = agentProgramRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("에이전트 프로그램을 찾을 수 없습니다. id: " + id));
        return toResponse(agentProgram);
    }

    public List<AgentProgramResponse> findByGptId(Long gptId) {
        return agentProgramRepository.findByGptId(gptId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<AgentProgramResponse> findByStatus(AgentStatus status) {
        return agentProgramRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AgentProgramResponse save(AgentProgramRequest request) {
        Gpt gpt = resolveGpt(request.getGptId());
        AgentProgram agentProgram = AgentProgram.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : AgentStatus.ACTIVE)
                .gpt(gpt)
                .build();
        return toResponse(agentProgramRepository.save(agentProgram));
    }

    @Transactional
    public AgentProgramResponse update(Long id, AgentProgramRequest request) {
        AgentProgram agentProgram = agentProgramRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("에이전트 프로그램을 찾을 수 없습니다. id: " + id));
        Gpt gpt = resolveGpt(request.getGptId());
        agentProgram.setName(request.getName());
        agentProgram.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            agentProgram.setStatus(request.getStatus());
        }
        agentProgram.setGpt(gpt);
        return toResponse(agentProgram);
    }

    @Transactional
    public void delete(Long id) {
        if (!agentProgramRepository.existsById(id)) {
            throw new EntityNotFoundException("에이전트 프로그램을 찾을 수 없습니다. id: " + id);
        }
        agentProgramRepository.deleteById(id);
    }

    // gptId가 null이면 GPT와 연결되지 않은 독립 에이전트 프로그램을 허용합니다.
    private Gpt resolveGpt(Long gptId) {
        if (gptId == null) {
            return null;
        }
        return gptRepository.findById(gptId)
                .orElseThrow(() -> new EntityNotFoundException("GPT를 찾을 수 없습니다. id: " + gptId));
    }

    private AgentProgramResponse toResponse(AgentProgram agentProgram) {
        return AgentProgramResponse.builder()
                .id(agentProgram.getId())
                .name(agentProgram.getName())
                .description(agentProgram.getDescription())
                .status(agentProgram.getStatus())
                .gptId(agentProgram.getGpt() != null ? agentProgram.getGpt().getId() : null)
                .gptName(agentProgram.getGpt() != null ? agentProgram.getGpt().getName() : null)
                .createdAt(agentProgram.getCreatedAt())
                .updatedAt(agentProgram.getUpdatedAt())
                .build();
    }
}
