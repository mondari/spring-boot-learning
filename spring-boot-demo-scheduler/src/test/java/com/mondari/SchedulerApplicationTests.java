package com.mondari;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.Duration;

@SpringBootTest
public class SchedulerApplicationTests {

    @SpyBean
    private SchedulerTask schedulerTask;

    @Test
    public void testReportCurrentTime() {
        Awaitility.await().atMost(Duration.ofSeconds(5)).untilAsserted(() -> {
            Mockito.verify(schedulerTask, Mockito.atLeast(5)).reportCurrentTime();
        });
    }

    @Test
    public void testReportRunTimes() {
        Awaitility.await().atMost(Duration.ofSeconds(10)).untilAsserted(() -> {
            Mockito.verify(schedulerTask, Mockito.atLeast(1)).reportRunTimes();
        });
    }

}
