package com.mondari.mapper;

import com.mondari.model.Post;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 博客文章表 Mapper 接口
 * </p>
 *
 * @author limondar
 * @since 2020-01-14
 */
public interface PostMapper {

    Post selectPostsForBlog(@Param("id") String id);

}
