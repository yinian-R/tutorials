package com.wymm.flume.core.rabbitmq;

import org.apache.flume.Context;
import org.apache.flume.Event;

public interface Converter {
    
    Event convert(Event source, Context context);
}
