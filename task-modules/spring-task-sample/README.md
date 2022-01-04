- `spring.task.scheduling` Spring Task 调度任务的配置，对应 `TaskSchedulingProperties` 配置类
- `TaskSchedulingAutoConfiguration` Spring 自动化配置类，实现 Spring Task 自动化配置，创建 `ThreadPoolTaskScheduler` 基于线程池的任务调度器。
本质上，`ThreadPoolTaskScheduler` 是基于 `ScheduledExecutorService` 的封装，增强在调度时间上的功能