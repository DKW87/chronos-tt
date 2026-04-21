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

    public static Settings defaults() {
        return Settings.builder()
                .daysWeek(5)
                .hoursDaily(8)
                .lastSelectedProject(null)
                .timeScale(TimeScale.THIRTY_MINUTES)
                .notifyOvertime(false)
                .aggregateProjectHours(true)
                .build();
    }

}
