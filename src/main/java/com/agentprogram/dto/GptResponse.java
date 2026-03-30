package com.agentprogram.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GptResponse {

    private Long id;
    private String name;
    private String model;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
