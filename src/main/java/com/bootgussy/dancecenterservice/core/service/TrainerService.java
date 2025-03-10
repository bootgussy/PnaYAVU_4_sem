package com.bootgussy.dancecenterservice.core.service;

import com.bootgussy.dancecenterservice.core.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TrainerService {

    private final List<Trainer> trainers = new ArrayList<>(
            List.of(
                    Trainer.builder()
                            .id(0L)
                            .name("Катя Вишня")
                            .phoneNumber("+375291111111")
                            .danceStyle("Waacking")
                            .build(),

                    Trainer.builder()
                            .id(1L)
                            .name("Иван Виноград")
                            .phoneNumber("+375292222222")
                            .danceStyle("Electro")
                            .build(),

                    Trainer.builder()
                            .id(2L)
                            .name("Саня Каштан")
                            .phoneNumber("+375293333333")
                            .danceStyle("Hip-Hop")
                            .build(),

                    Trainer.builder()
                            .id(3L)
                            .name("Оля Апельсин")
                            .phoneNumber("+375294444444")
                            .danceStyle("Electro")
                            .build()
            )
    );

    public Trainer findById(Long id) {
        return trainers.stream().filter(trainer -> trainer.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Trainer> findByDanceStyle(String danceStyle) {
        return trainers.stream().filter(trainer -> trainer.getDanceStyle().equals(danceStyle)).toList();
    }
}
