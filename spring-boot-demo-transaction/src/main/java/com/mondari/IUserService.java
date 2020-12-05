package com.mondari;

import org.springframework.transaction.annotation.Transactional;

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
public interface IUserService {

    List<Map<String, Object>> queryUserList();

    @Transactional(rollbackFor = Exception.class)
    void insertUsers(String... usernames);

    void truncateUser();
}
