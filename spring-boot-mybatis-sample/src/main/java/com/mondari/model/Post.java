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
 * 博客文章表
 * </p>
 *
 * @author limondar
 * @since 2020-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Post对象", description="博客文章表")
public class Post implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    @ApiModelProperty(value = "作者ID")
    private Integer authorId;

    @ApiModelProperty(value = "博客ID")
    private Integer blogId;

    @ApiModelProperty(value = "主题")
    private String subject;

    @ApiModelProperty(value = "章节")
    private String section;

    @ApiModelProperty(value = "草稿")
    private String draft;

    @ApiModelProperty(value = "内容")
    private String body;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除 0不删 1删除")
    private Boolean deleted;

    // 以下是新增内容

    @ApiModelProperty(value = "作者")
    private Author author;

    @ApiModelProperty(value = "评论")
    List<Comment> comments;

    @ApiModelProperty(value = "标签")
    List<Tag> tags;
}
