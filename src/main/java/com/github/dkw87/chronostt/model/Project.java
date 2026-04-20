package com.github.dkw87.chronostt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
public class Project {

    Long id;
    String name;
    String afasCode;
    Boolean billable;
    Boolean deleted;

}
