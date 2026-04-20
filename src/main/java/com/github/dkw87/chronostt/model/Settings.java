package com.github.dkw87.chronostt.model;

import com.github.dkw87.chronostt.enumeration.TimeScale;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    Integer daysWeek;
    Integer hoursDaily;
    Project lastSelectedProject;
    TimeScale timeScale;
    Boolean notifyOvertime;
    Boolean aggregateProjectHours;

}
