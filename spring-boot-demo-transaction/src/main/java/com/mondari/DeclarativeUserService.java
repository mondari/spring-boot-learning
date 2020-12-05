package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author limondar
 * @date 2020/12/5
 */
@Slf4j
@Service("DeclarativeUserService")
public class DeclarativeUserService implements IUserService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> queryUserList() {
        return jdbcTemplate.queryForList("SELECT * FROM USER");
    }

    @Override
    public void insertUsers(String... usernames) {
        for (String username : usernames) {
            log.info("新增用户：{}", username);
            jdbcTemplate.update("INSERT INTO USER(NAME) VALUES(?)", username);
        }
    }

    @Override
    public void truncateUser() {
        jdbcTemplate.execute("TRUNCATE TABLE USER");
    }
}
