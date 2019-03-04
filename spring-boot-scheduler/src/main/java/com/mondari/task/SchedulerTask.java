package com.mondari.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 参考Spring官网教程：https://spring.io/guides/gs/scheduling-tasks/
 */
@Component
public class SchedulerTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private int count = 0;

    /**
     * 固定频率定时任务
     */
    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        System.out.println("现在是北京时间：" + dateFormat.format(new Date()));
    }

    /**
     * cron 表达式可以用在线工具生成，具体格式如下：
     * 秒 分 时 日 月 星期 [年]
     * ? 表示不指定，* 表示任意
     * 例如：“0 0/5 8 ? ? 1,2,3,4,5” 表示每周一到周五上午8点0分0秒起每五分钟触发一次（闹钟）
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void process() {
        System.out.println("runs every 5 minute，already run " + (count++) + " times");
    }

}
