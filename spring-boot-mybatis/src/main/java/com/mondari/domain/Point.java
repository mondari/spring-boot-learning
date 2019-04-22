package com.mondari.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class Point {
    private Integer id;
    @NotEmpty(groups = {Insert.class}, message = "国际化key")
    private String name;
    private Double x;
    private Double y;
    private Double z;
    private Timestamp createTime;
    private Timestamp updateTime;

    public @interface Insert {
    }

    public @interface Update {
    }
}
