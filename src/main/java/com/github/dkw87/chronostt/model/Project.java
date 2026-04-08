package com.github.dkw87.chronostt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
