package com.wymm.springeasyexcelsample.core.excel.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用于对象中嵌套列表的结构合并导出
 * <p>
 * 相邻元素特定字段具有相同值进行列合并，特定字段判断使用 Function<T, String> groupBy
 */
public class ObjectAndListMergeStrategy<T> extends AbstractMergeStrategy {
    
    /**
     * 默认标题总行数 1
     */
    public static final Integer DEFAULT_HEADER_ROW_TOTAL = 1;
    /**
     * 合并列表
     */
    private final List<MergeRow> mergeRows = new ArrayList<>();
    /**
     * 忽略合并列
     */
    private final List<String> ignoreMergeColumnFiledNames = new ArrayList<>();
    private final List<String> includeMergeColumnFiledNames = new ArrayList<>();
    /**
     * 标题总行数
     */
    @Setter
    private Integer headerRowTotal;
    
    private final Function<T, String> groupBy;
    
    private final List<T> data = new ArrayList<>();
    
    /**
     * @param data    导出数据列表
     * @param groupBy 判断需合并的函数
     */
    public ObjectAndListMergeStrategy(Collection<T> data, Function<T, String> groupBy) {
        this.groupBy = groupBy;
        this.data.addAll(data);
    }
    
    /**
     * 添加导出数据
     *
     * @param data 导出数据列表
     * @return 完整的导出数据列表
     */
    public Collection<T> addData(Collection<T> data) {
        this.data.addAll(data);
        return this.data;
    }
    
    private Integer getHeaderRowTotal() {
        if (headerRowTotal == null) {
            return DEFAULT_HEADER_ROW_TOTAL;
        }
        return headerRowTotal;
    }
    
    /**
     * data to MergeRow List
     */
    private void usingDataSetUp() {
        if (data.isEmpty()) {
            return;
        }
        String groupKeyTemp = null;
        AtomicInteger groupItemTotal = new AtomicInteger(1);
        for (int i = 0; i < data.size(); i++) {
            T item = data.get(i);
            String groupKey = groupBy.apply(item);
            if (ObjectUtils.nullSafeEquals(groupKeyTemp, groupKey)) {
                groupItemTotal.incrementAndGet();
                this.addMergeRow(groupKey, i, groupItemTotal);
            } else {
                groupItemTotal = new AtomicInteger(1);
                groupKeyTemp = groupKey;
                this.addMergeRow(groupKey, i, groupItemTotal);
            }
            
        }
    }
    
    /**
     * @param unique      唯一标识
     * @param dataIndex   数据行标，从 0 开始递增
     * @param mergeRowNum 合并行数
     */
    private void addMergeRow(Object unique, Integer dataIndex, AtomicInteger mergeRowNum) {
        this.addMergeRow(new MergeRow(unique, dataIndex, mergeRowNum));
    }
    
    private void addMergeRow(MergeRow mergeRow) {
        boolean exists = mergeRows.stream().anyMatch(item -> item.getUnique().equals(mergeRow.getUnique()));
        if (!exists) {
            mergeRows.add(mergeRow);
        }
    }
    
    private Map<Integer, Integer> toMergeRowMap() {
        this.mergeRows.clear();
        this.usingDataSetUp();
        return mergeRows.stream()
                .collect(Collectors.toMap(item -> item.getDataIndex() + getHeaderRowTotal(), item -> item.getMergeRowNum().get()));
    }
    
    public void ignoreMergeColumnFiledNames(String... ignoreMergeColumnFiledName) {
        if (includeMergeColumnFiledNames.size() > 0) {
            throw new IllegalArgumentException("ignoreMergeColumnFiledNames and includeMergeColumnFiledNames select one to use");
        }
        ignoreMergeColumnFiledNames.addAll(Arrays.asList(ignoreMergeColumnFiledName));
    }
    
    public void includeMergeColumnFiledNames(String... includeMergeColumnFiledName) {
        if (ignoreMergeColumnFiledNames.size() > 0) {
            throw new IllegalArgumentException("ignoreMergeColumnFiledNames and includeMergeColumnFiledNames select one to use");
        }
        includeMergeColumnFiledNames.addAll(Arrays.asList(includeMergeColumnFiledName));
    }
    
    
    /**
     * merge
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        if (relativeRowIndex <= 0) {
            return;
        }
        if (ignoreMergeColumnFiledNames.size() > 0 && ignoreMergeColumnFiledNames.contains(head.getFieldName())) {
            return;
        }
        if (includeMergeColumnFiledNames.size() > 0 && !includeMergeColumnFiledNames.contains(head.getFieldName())) {
            return;
        }
        
        Map<Integer, Integer> mergeRowMap = toMergeRowMap();
        if (mergeRowMap.containsKey(relativeRowIndex)) {
            Integer mergeRowNum = mergeRowMap.get(relativeRowIndex);
            int firstRow = relativeRowIndex;
            int lastRow = relativeRowIndex + mergeRowNum - 1;
            if (firstRow == lastRow) {
                return;
            }
            CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow,
                    head.getColumnIndex(), head.getColumnIndex());
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }
    }
    
    
    @Getter
    public static class MergeRow {
        /**
         * 唯一标识
         */
        private final Object unique;
        
        /**
         * 行标
         */
        private final Integer dataIndex;
        
        /**
         * 合并行数
         */
        private final AtomicInteger mergeRowNum;
        
        public MergeRow(Object unique, Integer dataIndex, AtomicInteger mergeRowNum) {
            if (unique == null) {
                throw new IllegalArgumentException("unique is not can null");
            }
            if (dataIndex < 0) {
                throw new IllegalArgumentException("dataIndex must be greater than or equal 0");
            }
            if (mergeRowNum.get() < 1) {
                throw new IllegalArgumentException("mergeRowNum must be greater than or equal 1");
            }
            this.unique = unique;
            this.dataIndex = dataIndex;
            this.mergeRowNum = mergeRowNum;
        }
    }
}
