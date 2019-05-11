package com.mondari.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "点ID")
    private Integer id;

    /**
     * {@link NotEmpty} 只在插入时校验
     */
    @NotEmpty(groups = {Insert.class})
    @ApiModelProperty(value = "点名称")
    private String name;

    @NotNull(groups = {Insert.class})
    @ApiModelProperty(value = "点坐标x")
    private Double x;

    @NotNull(groups = {Insert.class})
    @ApiModelProperty(value = "点坐标y")
    private Double y;

    @NotNull(groups = {Insert.class})
    @ApiModelProperty(value = "点坐标z")
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
