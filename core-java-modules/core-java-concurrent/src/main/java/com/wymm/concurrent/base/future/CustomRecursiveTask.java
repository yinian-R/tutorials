package com.wymm.concurrent.base.future;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

@Slf4j
public class CustomRecursiveTask extends RecursiveTask<Integer> {
    
    private static final int THRESHOLD = 5;
    private int[] arr;
    
    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }
    
    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubTasks(arr))
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(arr);
        }
    }
    
    private int processing(int[] arr) {
        log.info("arr:" + Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining(",")));
        return Arrays.stream(arr)
                .filter(i -> i > 10 && i < 27)
                .sum();
    }
    
    private List<CustomRecursiveTask> createSubTasks(int[] arr) {
        ArrayList<CustomRecursiveTask> subTasks = new ArrayList<>();
        subTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)));
        subTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return subTasks;
    }
}
