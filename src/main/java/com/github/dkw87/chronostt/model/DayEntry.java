package com.github.dkw87.chronostt.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayEntry {

    Long id;
    LocalDate day;
    List<TimeEntry> timeEntries;
    Long secondsWorked;

}
