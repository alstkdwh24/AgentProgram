package com.agentprogram.dto;

import com.agentprogram.entity.AgentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentProgramResponse {

    private Long id;
    private String name;
    private String description;
    private AgentStatus status;
    private Long gptId;
    private String gptName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
