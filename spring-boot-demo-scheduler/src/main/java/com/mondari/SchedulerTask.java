package com.mondari;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class SchedulerTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private int count = 0;

    /**
     * 固定频率定时任务
     */
    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    /**
     * cron 表达式可以用在线工具生成，具体格式为<br>
     * 秒 分 时 日 月 星期 [年]<br>
     * ? 表示不指定，* 表示任意<br>
     * 例如：“0 0/5 8 ? ? 1,2,3,4,5” 表示每周一到周五上午8点0分0秒起每五分钟触发一次（闹钟）
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void reportRunTimes() {
        log.info("runs every 5 second，already run {} times", count++);
    }

}
