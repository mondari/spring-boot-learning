package com.mondari.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 书本
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @ApiModelProperty("书ID")
    private String id;

    @ApiModelProperty("书名")
    private String name;

    @ApiModelProperty("作者")
    private String writer;

    @ApiModelProperty("出版社")
    private String press;

    /**
     * 用 Float 类型会导致精度问题
     */
    @ApiModelProperty("价格")
    private Double price;
}