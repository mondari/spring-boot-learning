package com.mondari.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mondari.domain.Point;
import com.mondari.mapper.PointMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MybatisController {

    @Resource
    PointMapper pointMapper;

    /**
     * curl -X GET http://localhost:8080/demo
     *
     * @return "hello, world"
     */
    @GetMapping("demo")
    public String demo() {
        return "hello, world";
    }

    @ApiOperation("插入一个")
    @PostMapping("point")
    public Point insertOne(Point point) {
        pointMapper.insert(point);
        return point;
    }

    @ApiOperation("删除")
    @DeleteMapping("point")
    public int delete(Point point) {
        return pointMapper.delete(new QueryWrapper<>(point));
    }

    @ApiOperation("更新")
    @PutMapping("point")
    public int update(Point point) {
        return pointMapper.update(new Point().setX(point.getX()).setY(point.getY()).setZ(point.getZ()),
                new UpdateWrapper<>(new Point().setName(point.getName())));
    }

    @ApiOperation("查询")
    @GetMapping("point")
    public Point select(String name) {
        return pointMapper.selectOne(new QueryWrapper<Point>().lambda().eq(Point::getName, name));
    }

    @ApiOperation("批量插入")
    @PostMapping("points")
    public int batchInsert(@RequestBody List<Point> pointList) {
        return pointMapper.batchInsert(pointList);
    }

    @ApiOperation("批量更新")
    @PutMapping("points")
    public int batchUpdate(@RequestBody List<Point> pointList) {
        return pointMapper.batchUpdate(pointList);
    }

    @ApiOperation("分页查询（使用MyBatis-Plus）")
    @GetMapping("points/v1")
    public List<Point> findAllByV1(long current, long size) {
        IPage<Point> pages = pointMapper.selectPage(new Page<>(current, size), new QueryWrapper<>());
        return pages.getRecords();
    }

    @ApiOperation("分页查询（使用PageHelper）")
    @GetMapping("points/v2")
    public List<Point> findAllByV2(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Point> points = pointMapper.selectList(new QueryWrapper<>());  //分页查询结果
        PageInfo<Point> pageInfo = new PageInfo<>(points);  //包装一下可以获取分页信息
        return pageInfo.getList();  //这也是分页查询结果
    }


}
