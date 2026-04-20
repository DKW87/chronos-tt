package com.github.dkw87.chronostt.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    Long id;
    String name;
    String afasCode;
    Boolean billable;
    Boolean deleted;

}
