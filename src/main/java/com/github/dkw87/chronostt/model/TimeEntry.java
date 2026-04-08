package com.github.dkw87.chronostt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class TimeEntry {

    Long id;
    Project project;
    LocalDateTime start;
    LocalDateTime end;

}
