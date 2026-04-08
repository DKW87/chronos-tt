package com.github.dkw87.chronostt.model;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Settings {

    Integer daysWeek;
    Integer hoursDaily;
    TimeScale timeScale;
    Boolean notifyOverwork;
    Boolean aggregateProjectHours;

}
