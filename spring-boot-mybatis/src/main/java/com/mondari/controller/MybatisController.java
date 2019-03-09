package com.mondari.controller;

import com.mondari.domain.Point;
import com.mondari.mapper.PointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MybatisController {

    @Autowired
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

    @GetMapping("point")
    public Point findPointById(Integer id) {
        if (id == null) {
            return null;
        }
        return pointMapper.selectById(id);
    }

    @PostMapping("point")
    public Point insertOne(Point point) {
        if (point == null) {
            return null;
        }
        pointMapper.insertOne(point);
        return point;
    }
}
