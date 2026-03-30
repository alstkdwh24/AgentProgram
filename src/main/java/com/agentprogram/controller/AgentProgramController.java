package com.agentprogram.controller;

import com.agentprogram.dto.AgentProgramRequest;
import com.agentprogram.dto.AgentProgramResponse;
import com.agentprogram.entity.AgentStatus;
import com.agentprogram.service.AgentProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent-programs")
@RequiredArgsConstructor
public class AgentProgramController {

    private final AgentProgramService agentProgramService;

    @GetMapping
    public ResponseEntity<List<AgentProgramResponse>> findAll() {
        return ResponseEntity.ok(agentProgramService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentProgramResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(agentProgramService.findById(id));
    }

    @GetMapping("/gpt/{gptId}")
    public ResponseEntity<List<AgentProgramResponse>> findByGptId(@PathVariable Long gptId) {
        return ResponseEntity.ok(agentProgramService.findByGptId(gptId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AgentProgramResponse>> findByStatus(@PathVariable AgentStatus status) {
        return ResponseEntity.ok(agentProgramService.findByStatus(status));
    }

    @PostMapping
    public ResponseEntity<AgentProgramResponse> save(@Valid @RequestBody AgentProgramRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agentProgramService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentProgramResponse> update(@PathVariable Long id,
                                                        @Valid @RequestBody AgentProgramRequest request) {
        return ResponseEntity.ok(agentProgramService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agentProgramService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
