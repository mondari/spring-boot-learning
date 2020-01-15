package com.mondari.mapper;

import com.mondari.model.Author;
import com.mondari.model.Blog;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 博客表 Mapper 接口
 * </p>
 *
 * @author limondar
 * @since 2020-01-14
 */
public interface BlogMapper {

    Blog selectBlogDetails(@Param("id") String id);

    Blog selectBlog(@Param("id") String id);

}
