package com.wymm.springquartzsample.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskJob implements Job {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        log.info("this is a spring quartz job. taskId:"
                + jobDataMap.getInt(TaskUtils.PARAM_TASK_ID)
                + " name:"
                + jobDataMap.getString(TaskUtils.PARAM_NAME)
                + "\n"
                + "jobDataMap:"
                + objectMapper.writeValueAsString(jobDataMap));
        
    }
    
}
