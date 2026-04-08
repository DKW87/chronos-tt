package com.github.dkw87.chronostt.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TimeScale {
    SIX_MINUTES(6),
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30);

    private final int minutes;

}
