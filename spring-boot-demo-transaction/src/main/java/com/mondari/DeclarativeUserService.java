package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 声明式事务
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

    /**
     * 声明式事务入口及执行流程
     * <p>
     * {@link org.springframework.transaction.interceptor.TransactionInterceptor#invoke TransactionInterceptor.invoke()}-->
     * <br>
     * {@link org.springframework.transaction.interceptor.TransactionAspectSupport#invokeWithinTransaction TransactionAspectSupport.invokeWithinTransaction()}-->
     * <br>
     * {@link org.springframework.transaction.interceptor.TransactionAspectSupport#createTransactionIfNecessary TransactionAspectSupport.createTransactionIfNecessary()}-->
     * <br>
     * {@link org.springframework.transaction.support.AbstractPlatformTransactionManager#getTransaction AbstractPlatformTransactionManager.getTransaction()}-->
     * <br>
     * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager#doGetTransaction DataSourceTransactionManager.doGetTransaction()}-->
     * <br>
     * {@link org.springframework.transaction.interceptor.TransactionAspectSupport.InvocationCallback#proceedWithInvocation InvocationCallback.proceedWithInvocation()}-->
     * <br>
     * {@link org.springframework.aop.framework.ReflectiveMethodInvocation#proceed ReflectiveMethodInvocation.proceed()}-->
     * <br>
     * {@link org.springframework.transaction.interceptor.TransactionAspectSupport#commitTransactionAfterReturning TransactionAspectSupport.commitTransactionAfterReturning()}
     *
     * @param usernames
     */
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
