package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.api.dto.create.TrainerCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.TrainerResponseDto;
import com.bootgussy.dancecenterservice.core.mapper.TrainerMapper;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import com.bootgussy.dancecenterservice.core.service.TrainerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;

    @GetMapping("/{id}")
    public TrainerResponseDto findTrainerById(@PathVariable Long id) {
        Trainer trainer = trainerService.findTrainerById(id);
        return trainerMapper.toResponseDto(trainer);
    }

    @GetMapping
    public List<TrainerResponseDto> findAllTrainers() {
        List<Trainer> trainers = trainerService.findAllTrainers();
        return trainerMapper.toResponseDtoList(trainers);
    }

    @PostMapping
    public TrainerResponseDto createTrainer(@RequestBody TrainerCreateDto createDto) {
        Trainer trainer = trainerMapper.toEntity(createDto);
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        return trainerMapper.toResponseDto(createdTrainer);
    }

    @PutMapping("/{id}")
    public TrainerResponseDto updateTrainer(@PathVariable Long id, @RequestBody TrainerCreateDto createDto) {
        Trainer trainer = trainerMapper.toEntity(createDto);
        trainer.setId(id);
        Trainer updatedTrainer = trainerService.updateTrainer(trainer);
        return trainerMapper.toResponseDto(updatedTrainer);
    }

    @DeleteMapping("/{id}")
    public void deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
    }
}