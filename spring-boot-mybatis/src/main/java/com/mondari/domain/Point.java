package com.mondari.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Point {
    private Integer id;
    private String name;
    private Double x;
    private Double y;
    private Double z;
}
