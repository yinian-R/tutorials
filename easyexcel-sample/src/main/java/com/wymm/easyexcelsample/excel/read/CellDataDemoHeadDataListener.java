package com.wymm.easyexcelsample.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 读取头
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class CellDataDemoHeadDataListener implements ReadListener<CellDataReadDemoData> {
    private static final int BATCH_COUNT = 100;
    
    private List<CellDataReadDemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    
    @Override
    public void invoke(CellDataReadDemoData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }
    
    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        log.info("存储数据库成功！");
    }
}
