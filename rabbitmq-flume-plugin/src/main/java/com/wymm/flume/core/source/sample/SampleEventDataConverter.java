package com.wymm.flume.core.source.sample;

import com.wymm.flume.core.Converter;
import lombok.extern.slf4j.Slf4j;
import org.apache.flume.Context;
import org.apache.flume.Event;

/**
 * Sample Converter
 */
@Slf4j
public class SampleEventDataConverter implements Converter {
    
    @Override
    public Event convert(Event source, Context context) {
        log.info("执行转换数据");
        return source;
    }
}
