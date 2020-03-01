package com.mondari.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 博客表
 * </p>
 *
 * @author limondar
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Blog对象", description="博客表")
public class Blog implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    @ApiModelProperty(value = "作者ID")
    private Integer authorId;

    @ApiModelProperty(value = "博客名")
    private String title;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除 0不删 1删除")
    private Boolean deleted;

    // 以下是新增的字段

    @ApiModelProperty(value = "作者")
    private Author author;

    @ApiModelProperty(value = "博文")
    private List<Post> posts;

    public Blog(Integer id, Integer authorId) {
        this.id = id;
        this.authorId = authorId;
    }

}
