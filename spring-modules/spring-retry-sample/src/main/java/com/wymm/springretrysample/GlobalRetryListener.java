package com.wymm.springretrysample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalRetryListener extends RetryListenerSupport {

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.error("出现异常信息: 【{}】，即将重试第 {} 次", throwable.getMessage(), context.getRetryCount());
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.debug("开始执行方法，当前方法已设置重试操作");
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        if (throwable != null) {
            log.error("可重试操作最终失败");
        } else {
            log.info("可重试操作成功");
        }
    }
}
