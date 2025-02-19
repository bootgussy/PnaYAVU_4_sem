package dance.center.service.api.controller;

import dance.center.service.core.model.Trainer;
import dance.center.service.core.service.TrainerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    public Trainer findTrainerById(@RequestParam("id") Long id) {
        return trainerService.findTrainerById(id);
    }

    @GetMapping("/{danceStyle}")
    public List<Trainer> findTrainersByDanceStyle(@PathVariable String danceStyle) {
        return trainerService.findTrainersByDanceStyle(danceStyle);
    }
}
