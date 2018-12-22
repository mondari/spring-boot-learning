package com.mondari.springbootmongodb.controller;

import com.mondari.springbootmongodb.entity.Baike;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MongoCrontroller
 */
@RestController
public class MongoCrontroller {
    @Autowired
    MongoTemplate mongoTemplate;

    @ApiOperation("插入对象")
    @PostMapping("/baike/insert")
    public Baike insertBaike(Baike baike) {
        mongoTemplate.insert(baike, "baike");
        return baike;
    }

    @ApiOperation("插入或更新文档对象")
    @PostMapping("/baike/save")
    public Baike saveBaike(Baike baike) {
        mongoTemplate.save(baike, "baike");
        return baike;
    }

    @ApiOperation("修改对象")
    @PutMapping("/baike/{id}")
    public @ResponseBody
    String updateMulti(@PathVariable String id, String name) {
        Criteria criteria = Criteria.where("id").is(id);
        Update update = Update.update("name", name);
        // updateFirst：更新第一条匹配的数据；updateMulti：更新所有匹配的数据
        UpdateResult result = mongoTemplate.updateMulti(Query.query(criteria), update, Baike.class);
        return result.toString();
    }

    @ApiOperation("删除对象")
    @DeleteMapping("/baike/{name}")
    public Baike removeBaike(@PathVariable String name) {
        Baike baike = new Baike();
        baike.setName(name);
        mongoTemplate.remove(baike, "baike");
        return baike;
    }

    @ApiOperation("查找对象ById")
    @GetMapping("/baike/{id}")
    public Baike findBaikeById(@PathVariable String id) {
        return mongoTemplate.findById(id, Baike.class, "baike");
    }

    @ApiOperation("查找对象ByName")
    @GetMapping("/baike/{name}")
    public List<Baike> queryBaikeByName(String name) {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.find(Query.query(criteria), Baike.class);
    }

    @ApiOperation("分页查询")
    @GetMapping("/baikes/{pageNum}/{limitNum}")
    public List<Baike> findBaikes(@PathVariable int pageNum, @PathVariable int limitNum) {
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        //跳过个数
        int skip = (pageNum - 1) * limitNum;
        //构造分页查询语句
        query.skip(skip).limit(limitNum);
        List<Baike> list = mongoTemplate.find(query, Baike.class);
        return list;
    }

}