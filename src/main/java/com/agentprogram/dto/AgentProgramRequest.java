package com.agentprogram.dto;

import com.agentprogram.entity.AgentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentProgramRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    private AgentStatus status;

    private Long gptId;
}
