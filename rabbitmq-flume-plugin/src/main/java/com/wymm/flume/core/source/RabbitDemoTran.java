package com.wymm.flume.core.source;

import org.apache.flume.Context;
import org.apache.flume.Event;

public class RabbitDemoTran {
    public Event doTranslateData(Event event, Context context) {
        System.out.println("~~~~~~~~~~~~~~~RabbitDemoTran 转换数据》：");
        return event;
    }
}
