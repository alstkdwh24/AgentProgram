package com.agentprogram;

import com.agentprogram.entity.AgentProgram;
import com.agentprogram.entity.AgentStatus;
import com.agentprogram.entity.Gpt;
import com.agentprogram.repository.AgentProgramRepository;
import com.agentprogram.repository.GptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AgentProgramApplicationTests {

    @Autowired
    private GptRepository gptRepository;

    @Autowired
    private AgentProgramRepository agentProgramRepository;

    @Test
    @DisplayName("GPT 엔티티 저장 및 조회")
    void gptEntitySaveAndFind() {
        Gpt gpt = Gpt.builder()
                .name("GPT-4")
                .model("gpt-4-turbo")
                .description("OpenAI GPT-4 Turbo 모델")
                .build();

        Gpt saved = gptRepository.save(gpt);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("GPT-4");
        assertThat(saved.getModel()).isEqualTo("gpt-4-turbo");
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("에이전트 프로그램 엔티티 저장 및 조회")
    void agentProgramEntitySaveAndFind() {
        Gpt gpt = gptRepository.save(Gpt.builder()
                .name("GPT-3.5")
                .model("gpt-3.5-turbo")
                .build());

        AgentProgram agentProgram = AgentProgram.builder()
                .name("코드 리뷰 에이전트")
                .description("코드 리뷰를 수행하는 에이전트")
                .status(AgentStatus.ACTIVE)
                .gpt(gpt)
                .build();

        AgentProgram saved = agentProgramRepository.save(agentProgram);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("코드 리뷰 에이전트");
        assertThat(saved.getStatus()).isEqualTo(AgentStatus.ACTIVE);
        assertThat(saved.getGpt().getId()).isEqualTo(gpt.getId());
    }

    @Test
    @DisplayName("GPT ID로 에이전트 프로그램 조회")
    void findAgentProgramsByGptId() {
        Gpt gpt = gptRepository.save(Gpt.builder()
                .name("GPT-4o")
                .model("gpt-4o")
                .build());

        agentProgramRepository.save(AgentProgram.builder()
                .name("번역 에이전트")
                .status(AgentStatus.ACTIVE)
                .gpt(gpt)
                .build());

        agentProgramRepository.save(AgentProgram.builder()
                .name("요약 에이전트")
                .status(AgentStatus.INACTIVE)
                .gpt(gpt)
                .build());

        List<AgentProgram> programs = agentProgramRepository.findByGptId(gpt.getId());

        assertThat(programs).hasSize(2);
    }

    @Test
    @DisplayName("상태로 에이전트 프로그램 조회")
    void findAgentProgramsByStatus() {
        Gpt gpt = gptRepository.save(Gpt.builder()
                .name("GPT-4-mini")
                .model("gpt-4o-mini")
                .build());

        agentProgramRepository.save(AgentProgram.builder()
                .name("활성 에이전트")
                .status(AgentStatus.ACTIVE)
                .gpt(gpt)
                .build());

        agentProgramRepository.save(AgentProgram.builder()
                .name("비활성 에이전트")
                .status(AgentStatus.INACTIVE)
                .gpt(gpt)
                .build());

        List<AgentProgram> activePrograms = agentProgramRepository.findByStatus(AgentStatus.ACTIVE);

        assertThat(activePrograms).allMatch(p -> p.getStatus() == AgentStatus.ACTIVE);
    }
}
