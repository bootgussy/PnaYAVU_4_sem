package com.bootgussy.dancecenterservice.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Trainer {
    private Long id;

    private String name;

    private String phoneNumber;

    private String danceStyle;
}
