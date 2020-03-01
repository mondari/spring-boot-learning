package com.mondari.mapper;

import com.mondari.model.Author;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 作者表 Mapper 接口
 * </p>
 *
 * @author limondar
 * @since 2020-01-14
 */
public interface AuthorMapper {

    Author selectAuthor(@Param("id") String id);

}
