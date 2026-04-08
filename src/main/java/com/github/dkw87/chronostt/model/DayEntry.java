package com.github.dkw87.chronostt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DayEntry {

    Long id;
    LocalDate day;
    List<TimeEntry> timeEntries;
    Long minutesWorked;

}
