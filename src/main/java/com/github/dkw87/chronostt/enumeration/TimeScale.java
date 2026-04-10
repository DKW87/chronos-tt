package com.github.dkw87.chronostt.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeScale {
    SIX_MINUTES(6, "6 minutes"),
    FIFTEEN_MINUTES(15, "15 minutes"),
    THIRTY_MINUTES(30, "30 minutes");

    private final int minutes;
    private final String description;

}
