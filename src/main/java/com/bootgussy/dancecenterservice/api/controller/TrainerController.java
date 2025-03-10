package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.core.model.Trainer;
import com.bootgussy.dancecenterservice.core.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    public List<Trainer> getByDanceStyle(@RequestParam("danceStyle") String danceStyle) {
        return trainerService.findByDanceStyle(danceStyle);
    }

    @GetMapping("/{id}")
    public Trainer getById(@PathVariable Long id) {
        return trainerService.findById(id);
    }
}
