package com.mondari;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class TransactionApplicationTests {

    @Qualifier("DeclarativeUserService")
    @Autowired
    IUserService declarativeUserService;

    @Qualifier("ProgrammaticUserService")
    @Autowired
    IUserService programmaticUserService;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("声明式事务测试")
    @Test
    void testDeclarativeUserService() throws Exception {
        declarativeUserService.insertUsers("张三", "李四", "王二", "赵一");
        List<Map<String, Object>> queryForList = declarativeUserService.queryUserList();
        log.info(objectMapper.writeValueAsString(queryForList));
        Assertions.assertEquals(queryForList.size(), 4);

        try {
            declarativeUserService.insertUsers("我的名字很短", "我的名字很长，12个字符");
        } catch (Exception e) {
            log.error("新增用户失败：", e);
        }

        queryForList = declarativeUserService.queryUserList();
        log.info(objectMapper.writeValueAsString(queryForList));
        Assertions.assertEquals(queryForList.size(), 4);
    }

    @DisplayName("编程式事务测试")
    @Test
    void testProgrammaticUserService() throws Exception {
        programmaticUserService.insertUsers("张三", "李四", "王二", "赵一");
        List<Map<String, Object>> queryForList = declarativeUserService.queryUserList();
        log.info(objectMapper.writeValueAsString(queryForList));
        Assertions.assertEquals(queryForList.size(), 4);

        try {
            programmaticUserService.insertUsers("我的名字很短", "我的名字很长，12个字符");
        } catch (Exception e) {
            log.error("新增用户失败：", e);
        }

        queryForList = programmaticUserService.queryUserList();
        log.info(objectMapper.writeValueAsString(queryForList));
        Assertions.assertEquals(queryForList.size(), 4);
    }

    @BeforeAll
    static void initAll() {
        log.info("------BeforeAll------");
    }

    @BeforeEach
    void init() {
        log.info("------BeforeEach------");
        declarativeUserService.truncateUser();
        log.info("----End BeforeEach----");
    }

    @AfterEach
    void tearDown() {
        log.info("------AfterEach------");

    }

    @AfterAll
    static void tearDownAll() {
        log.info("------AfterAll------");

    }

}
