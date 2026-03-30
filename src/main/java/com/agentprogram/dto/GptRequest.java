package com.agentprogram.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GptRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "모델명은 필수입니다.")
    @Size(max = 100)
    private String model;

    @Size(max = 500)
    private String description;
}
