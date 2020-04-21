package com.example.demo;

import com.example.demo.JDKProxy.CaiXuKun;
import com.example.demo.JDKProxy.Star;
import com.example.demo.JDKProxy.StarProxy;
import com.example.demo.task.HelloJob;
import com.example.demo.task.HelloScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void schedulerTest() throws SchedulerException {
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
    /**
     * 1、new一个目标对象
     *
     * 2、new一个InvocationHandler，将目标对象set进去
     *
     * 3、通过CreatProxyObj创建代理对象，强转为目标对象的接口类型即可使用，实际上生成的代理对象实现了目标接口。
     */
    @Test
    public void proxyTest() {
        Star caiXuKun = new CaiXuKun();
        StarProxy starProxy = new StarProxy();
        starProxy.setTarget(caiXuKun);
        Object obj = starProxy.creatProxyObj();
        Star star = (Star)obj;
        System.out.println(star.sing("蔡徐坤"));
        System.out.println(star.dance("蔡徐坤"));
    }

}
