package com.wymm.easyexcelsample.excel.merger.strategy;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MergeStrategy extends AbstractMergeStrategy {
    /**
     * 默认标题总行数 1
     */
    public static final Integer DEFAULT_HEADER_ROW_TOTAL = 1;
    
    /**
     * 标题总行数
     */
    @Setter
    private Integer headerRowTotal = DEFAULT_HEADER_ROW_TOTAL;
    
    /**
     * 合并列表
     */
    private final List<MergeRow> mergeRows = new ArrayList<>();
    
    /**
     * 忽略合并行
     */
    private final List<String> ignoreMergeRows = new ArrayList<>();
    
    /**
     * @param unique      唯一标识
     * @param dataIndex   数据行标，从 0 开始递增
     * @param mergeRowNum 合并行数
     */
    public void addMergeRow(Object unique, Integer dataIndex, Integer mergeRowNum) {
        this.addMergeRow(new MergeRow(unique, dataIndex, mergeRowNum));
    }
    
    public void addMergeRow(MergeRow mergeRow) {
        boolean exists = mergeRows.stream().anyMatch(item -> item.getUnique().equals(mergeRow.getUnique()));
        if (!exists) {
            mergeRows.add(mergeRow);
        }
    }
    
    public Map<Integer, Integer> toMergeRowMap() {
        return mergeRows.stream()
                .collect(Collectors.toMap(item -> item.getDataIndex() + headerRowTotal, MergeRow::getMergeRowNum));
    }
    
    public void addIgnoreMergeRow(String... ignoreMergeRow) {
        ignoreMergeRows.addAll(Arrays.asList(ignoreMergeRow));
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
        if (ignoreMergeRows.contains(head.getFieldName())) {
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
        private final Integer mergeRowNum;
        
        public MergeRow(Object unique, Integer dataIndex, Integer mergeRowNum) {
            if (unique == null) {
                throw new IllegalArgumentException("unique is not can null");
            }
            if (dataIndex < 0) {
                throw new IllegalArgumentException("dataIndex must be greater than or equal 0");
            }
            if (mergeRowNum < 1) {
                throw new IllegalArgumentException("mergeRowNum must be greater than or equal 1");
            }
            this.unique = unique;
            this.dataIndex = dataIndex;
            this.mergeRowNum = mergeRowNum;
        }
    }
}
