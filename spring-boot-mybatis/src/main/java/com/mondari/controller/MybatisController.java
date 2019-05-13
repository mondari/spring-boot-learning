package com.mondari.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mondari.dao.PointDao;
import com.mondari.mapper.PointMapper;
import com.mondari.model.Point;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
public class MybatisController {

    @Resource
    PointMapper pointMapper;

    private final PointDao pointDao;

    @Autowired
    public MybatisController(PointDao pointDao) {
        this.pointDao = pointDao;
    }

    @ApiOperation("插入一个")
    @PostMapping("point")
    public Point insertOne(@Validated({Point.Insert.class}) Point point) {
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

    @ApiOperation("分页批量插入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "-1", required = true, dataType = "int", paramType = "query")
    })
    @PostMapping("points")
    public int batchInsert(@RequestBody @NotEmpty List<Point> pointList, int pageSize) {
        return pointDao.pageBatchOperate(pointList, pointMapper, pageSize, PointMapper::batchInsert);
    }

    @ApiOperation("分页批量更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "-1", required = true, dataType = "int", paramType = "query")
    })
    @PutMapping("points")
    public int batchUpdate(@RequestBody @NotEmpty List<Point> pointList, int pageSize) {
        return pointDao.pageBatchOperate(pointList, pointMapper, pageSize, PointMapper::batchUpdate);
    }

    @ApiOperation("分页批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "-1", required = true, dataType = "int", paramType = "query")
    })
    @DeleteMapping("points")
    public int batchDelete(@RequestBody @NotEmpty List<Point> pointList, int pageSize) {
        return pointDao.pageBatchOperate(pointList, pointMapper, pageSize, PointMapper::batchDelete);
    }

    @ApiOperation("分页查询（使用MyBatis-Plus）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", defaultValue = "-1", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "分页大小", defaultValue = "-1", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("points/v1")
    public IPage<Point> findAllV1(long current, long size) {
        IPage<Point> pages = pointMapper.selectPage(new Page<>(current, size), new QueryWrapper<>());
        pages.getRecords();//这是分页查询的结果
        return pages;
    }

    @ApiOperation("分页查询（使用PageHelper）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "-1", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "0", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("points/v2")
    public List<Point> findAllV2(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //分页查询结果
        List<Point> points = pointMapper.selectList(new QueryWrapper<>());
        //包装一下可以获取分页信息
        PageInfo<Point> pageInfo = new PageInfo<>(points);
        //这也是分页查询结果
        return pageInfo.getList();
    }

    @ApiOperation("Druid 数据源监控")
    @GetMapping("/druid/stat")
    public Object druidStat() {
        /**
         * DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，
         * 除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
         */
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
