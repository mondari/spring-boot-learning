package com.mondari.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class PointDao {

    /**
     * 分页批量操作（增删改）
     * <p>
     * 1.Spring的默认的事务规则是在遇到运行时异常（RuntimeException）和程序错误（Error）才会回滚，
     * 如果要在遇到检查异常(checked exception)回滚，需要通过 roolbackFor 属性来指定，
     * 如 @Transactional(roolbackFor = {Exception.class})
     * <p>
     * 2.@Transactional 注解加在 Controller 层的方法上会导致事务失效
     * <p>
     * 3.尽量不要在业务层捕捉异常并处理，因为会导致事务失效。推荐做法：在业务层统一抛出异常，然后在控制层统一处理。
     *
     * @param dataList
     * @param pageSize
     * @param function 函数式接口
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public <T, U> int pageBatchOperate(List<T> dataList, U mapper, int pageSize, BiFunction<U, List<T>, Integer> function) {
        if (CollectionUtils.isEmpty(dataList)) {
            return 0;
        }
        if (pageSize <= 0) {
            // 分页大小不为0，则不分页
            return function.apply(mapper, dataList);
        }

        int dataSize = dataList.size();
        int pages = dataSize / pageSize + (dataSize % pageSize == 0 ? 0 : 1);//页数
        int successCount = 0;//成功操作条数
        for (int i = 0; i < pages; i++) {
            int fromIndex = pageSize * i;
            int toIndex = pageSize * (i + 1);
            List<T> subList = dataList.subList(fromIndex, Math.min(toIndex, dataSize));
            successCount += function.apply(mapper, subList);
        }
        return successCount;
    }

}
