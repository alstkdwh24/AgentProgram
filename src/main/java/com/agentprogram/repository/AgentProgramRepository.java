package com.agentprogram.repository;

import com.agentprogram.entity.AgentProgram;
import com.agentprogram.entity.AgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentProgramRepository extends JpaRepository<AgentProgram, Long> {

    List<AgentProgram> findByGptId(Long gptId);

    List<AgentProgram> findByStatus(AgentStatus status);
}
