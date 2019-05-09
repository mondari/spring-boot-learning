package com.mondari.controller;

import com.mondari.model.Book;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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
     * 注意下面两个POST请求的参数都不要加“@RequestBody”注解。这表明是query参数，但是和加“@RequestParam”还是有区别的，因为Book是个实体类。
     *
     * @param book
     * @return
     */
    @ApiOperation("插入对象")
    @PostMapping("/book/insert")
    public Book insertOne(Book book) {
        return mongoTemplate.insert(book, "book");
    }

    @ApiOperation("插入或更新对象")
    @PostMapping("/book/save")
    public Book saveOne(Book book) {
        return mongoTemplate.save(book, "book");
    }

    @ApiOperation("批量插入")
    @PostMapping("/book/insertMulti")
    public Collection<Book> insertMulti(@RequestBody List<Book> books) {
        return mongoTemplate.insertAll(books);

        // 另外一种方法：
        // BulkOperations.BulkMode.UNORDERED 是并行模式
//        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Book.class, "book");
//        bulkOps.insert(books);
//        bulkOps.execute();
//        return books;
    }

    @ApiOperation("更新对象")
    @PutMapping("/book")
    public String update(Book book) {
        Criteria criteria = Criteria.where("name").is(book.getName());
        Update update = Update.update("writer", book.getWriter());// 内部实现：Update update = new Update().set("name", name)
        update.set("press", book.getPress()).set("price", book.getPrice());
        // updateFirst()：更新第一条匹配的数据；updateMulti()：更新所有匹配的数据
        UpdateResult result = mongoTemplate.updateFirst(Query.query(criteria), update, Book.class);
        return result.toString();
    }

    @ApiOperation("删除对象")
    @DeleteMapping("/book")
    public Book remove(@RequestParam String name) {
        Book book = new Book();
        book.setName(name);
        mongoTemplate.remove(book, "book");
        return book;
    }

    @ApiOperation("根据ID查找一个对象")
    @GetMapping("/book/id")
    public Book findById(@RequestParam String id) {
        return mongoTemplate.findById(id, Book.class, "baike");
    }

    @ApiOperation("根据名称查找一个对象")
    @GetMapping("/book/name")
    public Book findByName(@RequestParam String name) {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.findOne(Query.query(criteria), Book.class);
    }

    @ApiOperation("根据名称查找多个对象")
    @GetMapping("/books/name")
    public List<Book> findAllByName(@RequestParam String name) {
        Criteria criteria = Criteria.where("name").is(name);
        return mongoTemplate.find(Query.query(criteria), Book.class);
    }

    @ApiOperation("分页查询")
    @GetMapping("/books")
    public List<Book> findAll(@RequestParam int pageNum, @RequestParam int limitNum) {
        Criteria criteria = new Criteria();
        Query query = Query.query(criteria);
        //跳过个数
        int skip = (pageNum - 1) * limitNum;
        //构造分页查询语句
        query.skip(skip).limit(limitNum);
        return mongoTemplate.find(query, Book.class);
    }

}