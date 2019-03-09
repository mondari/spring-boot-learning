package com.mondari.domain;

import lombok.Data;

@Data
public class Point {
    private String id;
    private String name;
    private Double x;
    private Double y;
    private Double z;
}
