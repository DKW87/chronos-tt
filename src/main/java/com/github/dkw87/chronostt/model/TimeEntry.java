package com.github.dkw87.chronostt.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeEntry {

    Long id;
    Project project;
    LocalDateTime start;
    LocalDateTime end;

}
