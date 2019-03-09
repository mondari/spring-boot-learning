package com.mondari.mapper;

import com.mondari.domain.Point;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {

    Point selectById(int id);

    int insertOne(Point point);
}
