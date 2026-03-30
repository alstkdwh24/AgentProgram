package com.agentprogram.repository;

import com.agentprogram.entity.Gpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GptRepository extends JpaRepository<Gpt, Long> {

    Optional<Gpt> findByName(String name);

    List<Gpt> findByModel(String model);
}
