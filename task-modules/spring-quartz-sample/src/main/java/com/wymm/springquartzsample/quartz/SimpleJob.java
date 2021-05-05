package com.wymm.springquartzsample.quartz;

import org.quartz.*;

public class SimpleJob implements Job {
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("This is a quartz job");
    
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String jobSays = jobDataMap.getString("jobSays");
        float myFloatValue = jobDataMap.getFloat("myFloatValue");
    
        System.out.println("Job says: " + jobSays + ", and val is: " + myFloatValue);
    }
    
}
