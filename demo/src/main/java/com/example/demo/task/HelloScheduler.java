package com.example.demo.task;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description: TODO
 * @Auther: zhipeng.han@luckincoffee.com
 * @Date: 2019/2/11 16:40
 */
public class HelloScheduler {

    public static void scheduler() throws SchedulerException {
        //创建一个jobDetail的实例，将该实例与HelloJob Class绑定
        JobDetail myJob = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();

        //创建一个Trigger触发器的实例，定义该job立即执行，并且每2秒执行一次，一直执行
        SimpleTrigger myTrigger =
                TriggerBuilder.newTrigger().withIdentity("myTrigger").startNow().withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(
                CronScheduleBuilder.cronSchedule("* * * * * ? ")).build();
        //创建schedule实例
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.start();
        scheduler.scheduleJob(myJob,cronTrigger);
    }
}
