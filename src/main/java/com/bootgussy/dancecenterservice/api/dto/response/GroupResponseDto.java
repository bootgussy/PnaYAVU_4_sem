package com.bootgussy.dancecenterservice.api.dto.response;

import java.util.List;
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
public class GroupResponseDto {
    private Long id;

    private String difficulty;

    private Long trainerId;

    private List<Long> studentsId;
}
