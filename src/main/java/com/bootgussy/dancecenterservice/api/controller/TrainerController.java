package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.core.model.Trainer;
import com.bootgussy.dancecenterservice.core.service.TrainerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
