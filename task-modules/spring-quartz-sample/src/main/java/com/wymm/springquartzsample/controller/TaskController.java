package com.wymm.springquartzsample.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RequestMapping("/task")
@RestController
public class TaskController {
    
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    @PostMapping
    public void addTask(String name, Integer taskId) throws SchedulerException {
        startTask(name, taskId);
    }
    
    @PutMapping
    public void editTask(String name, Integer taskId) throws SchedulerException {
        startTask(name, taskId);
    }
    
    private void startTask(String name, Integer taskId) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        
        scheduler.scheduleJob(
                JobBuilder
                        .newJob(TaskJob.class)
                        .withIdentity(TaskUtils.TASK_NAME_PREFIX + taskId, TaskUtils.TASK_GROUP)
                        .usingJobData(TaskUtils.PARAM_TASK_ID, taskId)
                        .usingJobData(TaskUtils.PARAM_NAME, name)
                        .build(),
                Collections.singleton(TriggerBuilder.newTrigger()
                        .withIdentity(TaskUtils.TASK_NAME_PREFIX + taskId, TaskUtils.TASK_GROUP)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                        .build()),
                true
        );
        scheduler.start();
    }
    
    @DeleteMapping
    public void deleteTask(Integer taskId) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.deleteJob(new JobKey(TaskUtils.TASK_NAME_PREFIX + taskId, TaskUtils.TASK_GROUP));
    }
    
    @GetMapping
    public void getList() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
    }
    
}
