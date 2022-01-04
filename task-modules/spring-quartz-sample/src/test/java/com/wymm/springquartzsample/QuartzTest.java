package com.wymm.springquartzsample;

import com.wymm.springquartzsample.quartz.SimpleJob;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QuartzTest {
    
    @Test
    void scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        
        // scheduler 可以用于添加、删除、列出 Jobs 和 Triggers
        
        scheduler.shutdown();
    }
    
    /**
     * 当该 Job 被触发时，调度程序的工作线程之一将调用 execute() 方法，传递给此方法的 JobExecutionContext 对象为作业实例提供有关其
     * 运行时环境的信息、执行它的 Scheduler、触发执行的 Trigger、Job 的 JobDataMap 对象以及其它一些项
     */
    @Test
    void jobDetail() {
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("myJob", "group1")
                .build();
    }
    
    /**
     * JobDataMap 用于保存我们希望在作业实例执行时提供给它的任何数量的数据对象。
     * 在 Job 添加到调度程序之前，在构建 JobDetail 时将数据放入 JobDataMap
     */
    @Test
    void jobDataMap() {
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("myJob", "group1")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();
    }
    
    /**
     * Trigger 用于触发 Job 的执行
     * Quartz 附带了几种不同的触发器类型，但是最常用的是 SimpleTrigger 和 CronTrigger
     */
    @Test
    void trigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();
    }
    
    /**
     * 不奏效的指令
     * 如果持久触发器由于调度程序被关闭而错过了触发时间，或者Quartz的线程池中没有可用线程，则会发生不奏效。
     * 调度程序启动时，将搜索任何未触发的持久性触发器。之后，它会根据它们各自配置的不奏效指令来更新它们中的每一个。
     */
    @Test
    void misfireInstructions() {
        Trigger misFiredTriggerA = TriggerBuilder.newTrigger()
                .startAt(DateUtils.addSeconds(new Date(), -10))
                .build();
        
        Trigger misFiredTriggerB = TriggerBuilder.newTrigger()
                .startAt(DateUtils.addSeconds(new Date(), -10))
                // 智能策略：withMisfireHandlingInstructionFireNow()，这意味着在调度程序发现不奏效后立即执行作业
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withMisfireHandlingInstructionFireNow())
                .build();
    }
    
    /**
     * SimpleTrigger 用于在特定时间执行作业的方案。这可以是一次，也可以以特定的间隔重复执行。
     */
    @Test
    void simpleTrigger() {
        // 执行一次
        Date myStartTime = new Date(2021, 4, 5, 0, 35, 0);
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(myStartTime)
                .forJob("job1", "group1")
                .build();
        
        // 每10秒重复10次
        SimpleTrigger trigger2 = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(myStartTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(10))
                .forJob("job1")
                .build();
    }
    
    /**
     * 当我们需要基于类似日历的语句的时间表时，使用 CronTrigger，例如每天8点至17点之间每隔一分钟触发一次
     */
    void cronTrigger() {
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
                .forJob("myJob", "group1")
                .build();
    }
    
    /**
     * Scheduler、JobDetail、Trigger 创建和启动任务
     */
    @Test
    void usingQuartz() throws InterruptedException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            
            // scheduler 可以用于添加、删除、列出 Jobs 和 Triggers
            JobDetail job = JobBuilder.newJob(SimpleJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData("jobSays", "Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();
            
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .build();
            
            scheduler.scheduleJob(job, trigger);
            
            CountDownLatch latch = new CountDownLatch(1);
            latch.await(30L, TimeUnit.SECONDS);
            
            // 删除任务
            //scheduler.deleteJob(new JobKey("myJob", "group1"));
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
