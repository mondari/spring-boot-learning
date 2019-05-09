package com.mondari.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mondari.model.Point;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointMapper extends BaseMapper<Point> {
    int batchInsert(List<Point> pointList);

    int batchUpdate(List<Point> pointList);

    int batchDelete(List<Point> pointList);
}
