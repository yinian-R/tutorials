package com.wymm.easyexcelsample.excel.simple;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.wymm.easyexcelsample.excel.DemoData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {
    /**
     * When analysis one row trigger invoke function.
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context analysis context
     */
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("invoke data:" + data);
    }
    
    /**
     * if have something to do after all analysis
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("doAfterAllAnalysed");
    }
}
