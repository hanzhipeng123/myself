package com.example.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * @Author zhipeng.han@luckincoffee.com
 * @date 2020/4/21 13:56
 * @description :
 */
@Component
public class AnnotationJob {

    @Scheduled(cron = "* * * * * ? ")
    public void cronJob() {
        System.out.println(System.currentTimeMillis() + " ...>>cron....");
    }
}
