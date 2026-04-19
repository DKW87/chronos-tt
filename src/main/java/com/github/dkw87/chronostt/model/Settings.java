package com.github.dkw87.chronostt.model;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class Settings {

    Integer daysWeek;
    Integer hoursDaily;
    Project lastSelectedProject;
    TimeScale timeScale;
    Boolean notifyOvertime;
    Boolean aggregateProjectHours;

}
