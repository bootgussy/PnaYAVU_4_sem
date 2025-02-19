package dance.center.service.core.service;

import dance.center.service.core.model.Trainer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    private final List<Trainer> trainerRepository = new ArrayList<>(
            List.of(
                    Trainer.builder()
                            .id(0L)
                            .name("Ваня Персик")
                            .danceStyle("Electro")
                            .phoneNumber("+375291111111")
                            .build(),

                    Trainer.builder()
                            .id(1L)
                            .name("Настя Яблоко")
                            .danceStyle("Waacking")
                            .phoneNumber("+375292222222")
                            .build(),

                    Trainer.builder()
                            .id(2L)
                            .name("Лёша Абрикос")
                            .danceStyle("Electro")
                            .phoneNumber("+375293333333")
                            .build(),

                    Trainer.builder()
                            .id(3L)
                            .name("Соня Вишня")
                            .danceStyle("Vogue")
                            .phoneNumber("+375294444444")
                            .build()
            )
    );
    private Long lastId;

    public Trainer findTrainerById(Long id) {
        return trainerRepository.stream()
                .filter(currentTrainer -> currentTrainer.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Trainer> findTrainersByDanceStyle(String danceStyle) {
        return trainerRepository.stream()
                .filter(currentTrainer -> currentTrainer.getDanceStyle().equals(danceStyle))
                .toList();
    }
}
