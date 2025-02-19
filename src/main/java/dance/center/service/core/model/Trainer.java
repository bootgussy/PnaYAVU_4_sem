package dance.center.service.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    private Long id;

    private String name;

    private String phoneNumber;

    private String danceStyle;
}
