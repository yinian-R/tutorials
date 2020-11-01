package com.wymm.webflux;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class ReactorDemo {
    public static void main(String[] args) {
        // reactor = jdk8 stream + jdk9 reactive stream
        // Mono 0-9 个元素
        // Flux 0-N 个元素
        
        String[] strs = {"1", "2", "3"};
        
        // 定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            
            private Subscription subscription;
            
            @Override
            public void onSubscribe(Subscription s) {
                // 保持订阅关系，用它来给发布者相应
                this.subscription = s;
                // 请求一个数据
                this.subscription.request(1);
            }
            
            @Override
            public void onNext(Integer integer) {
                // 接收到数据，进行处理
                System.out.println("接受到数据：" + integer);
                
                // 处理完，调用request再请求数据
                this.subscription.request(1);
                
                // 或者已经达到目标，调用 cancel 方法告诉发布者不再接受数据了
                //this.subscription.cancel();
            }
            
            @Override
            public void onError(Throwable t) {
                // 出现了异常（例如在 onNext 方法处理数据时产生了异常）
                t.printStackTrace();
                
                // 可以告诉发布者，不再接收数据
                this.subscription.cancel();
            }
            
            @Override
            public void onComplete() {
                // 全部数据处理完毕，也就是发布者关闭了
                System.out.println("数据处理完毕");
            }
        };
        
        // 这里是 jdk8 的 stream
        Flux.fromArray(strs).map(Integer::parseInt)
                // 最终操作，这里是 jdk9 的 reactive steam
                .subscribe(subscriber);
    }
}
