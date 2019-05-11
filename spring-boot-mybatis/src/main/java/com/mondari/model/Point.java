package com.mondari.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Accessors(chain = true)
public class Point {
    /**
     * id 属性无需反序列化
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @NotEmpty(groups = {Insert.class})
    private String name;
    @NotNull(groups = {Insert.class})
    private Double x;
    @NotNull(groups = {Insert.class})
    private Double y;
    @NotNull(groups = {Insert.class})
    private Double z;
    /**
     * 数据库会自动创建时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createTime;
    /**
     * 数据库会自动更新时间
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp updateTime;

    public @interface Insert {
    }

    public @interface Update {
    }
}
