package com.bootgussy.dancecenterservice.core.repository;

import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByTrainerAndDifficulty(Trainer trainer, String difficulty);
}
