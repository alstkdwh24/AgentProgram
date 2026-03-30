package com.agentprogram.controller;

import com.agentprogram.dto.GptRequest;
import com.agentprogram.dto.GptResponse;
import com.agentprogram.service.GptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpts")
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    @GetMapping
    public ResponseEntity<List<GptResponse>> findAll() {
        return ResponseEntity.ok(gptService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GptResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(gptService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GptResponse> save(@Valid @RequestBody GptRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gptService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GptResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody GptRequest request) {
        return ResponseEntity.ok(gptService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gptService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
