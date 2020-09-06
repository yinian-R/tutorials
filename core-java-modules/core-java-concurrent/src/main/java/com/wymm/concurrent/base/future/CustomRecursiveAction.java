package com.wymm.concurrent.base.future;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * 仅为演示 RecursiveAction 把结果转换大写打印。
 */
@Slf4j
public class CustomRecursiveAction extends RecursiveAction {
    
    private static final int THRESHOLD = 4;
    private String workload = "";
    
    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }
    
    /**
     * 如果 workload.length() > THRESHOLD 则使用 createSubTasks() 方法拆分任务 
     */
    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubTasks(workload));
        } else {
            processing(workload);
        }
    }
    
    private List<CustomRecursiveAction> createSubTasks(String workload) {
        List<CustomRecursiveAction> subTasks = new ArrayList<>();
        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2);
        subTasks.add(new CustomRecursiveAction(partOne));
        subTasks.add(new CustomRecursiveAction(partTwo));
        return subTasks;
    }
    
    private void processing(String workload) {
        String result = workload.toUpperCase();
        log.info("This result:" + result);
    }
}
