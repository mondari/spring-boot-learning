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
 * MongoController
 */
@RestController
public class MongoController {
    private final
    MongoTemplate mongoTemplate;

    @Autowired
    public MongoController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 注意下面两个POST请求的参数都不要加“@RequestBody”注解。这表明是query参数，但是和加“@RequestParam”还是有区别的，因为Baike是个实体类。
     *
     * @param baike
     * @return
     */
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

    /**
     * 参数name虽然没加注解“@RequestParam”，但是默认还是query参数
     *
     * @param id
     * @param name
     * @return
     */
    @ApiOperation("修改对象")
    @PutMapping("/baike/{id}")
    public String updateMulti(@PathVariable String id, String name) {
        Criteria criteria = Criteria.where("id").is(id);
        Update update = Update.update("name", name);// 内部实现：Update update = new Update().set("name", name)
        // updateFirst()：更新第一条匹配的数据；updateMulti()：更新所有匹配的数据
        UpdateResult result = mongoTemplate.updateMulti(Query.query(criteria), update, Baike.class);
        return result.toString();
    }

    @ApiOperation("删除对象")
    @DeleteMapping("/baike")
    public Baike removeBaike(@RequestParam String name) {
        Baike baike = new Baike();
        baike.setName(name);
        mongoTemplate.remove(baike, "baike");
        return baike;
    }

    @ApiOperation("根据ID查找一个对象")
    @GetMapping("/findById")
    public Baike findBaikeById(@RequestParam String id) {
        return mongoTemplate.findById(id, Baike.class, "baike");
    }

    @ApiOperation("根据名称查找一个对象")
    @GetMapping("/findOne")
    public Baike queryBaikeByName(@RequestParam String name) {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.findOne(Query.query(criteria), Baike.class);
    }

    @ApiOperation("根据名称查找多个对象")
    @GetMapping("/find")
    public List<Baike> queryBaikesByName(@RequestParam String name) {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.find(Query.query(criteria), Baike.class);
    }

    @ApiOperation("分页查询")
    @GetMapping("/baikes")
    public List<Baike> queryBaikes(@RequestParam int pageNum, @RequestParam int limitNum) {
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        //跳过个数
        int skip = (pageNum - 1) * limitNum;
        //构造分页查询语句
        query.skip(skip).limit(limitNum);
        return mongoTemplate.find(query, Baike.class);
    }

}