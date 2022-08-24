package com.wymm.date;

import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 日期固定范围填充类
 */
public class TimePeriod {
    
    /**
     * 0        yyyy-MM-dd 00:00:00
     * 86399    yyyy-MM-dd 23:59:59
     */
    private final BitSet fillBitSet;
    /**
     * 填充范围最小值 include
     */
    private final LocalDateTime min;
    /**
     * 填充范围最大值 include
     */
    private final LocalDateTime max;
    /**
     * BitSet.set include
     */
    private final Integer minPoint;
    /**
     * BitSet.set  include
     */
    private final Integer maxPoint;
    /**
     * 填充集合
     */
    private final Set<TimePeriodItem> fillTreeSet;
    private final Set<TimePeriodItem> notFillTreeSet;
    
    
    public TimePeriod(LocalDate date) {
        this(date.atStartOfDay(), LocalDateTime.of(date, LocalTime.MAX));
    }
    
    public TimePeriod(LocalDateTime min, LocalDateTime max) {
        this.min = min;
        this.max = max;
        this.minPoint = TimePeriodConvertor.getPoint(this.min, min);
        this.maxPoint = TimePeriodConvertor.getPoint(this.min, max);
        this.fillBitSet = new BitSet(this.maxPoint);
        this.fillTreeSet = new TreeSet<>(Comparator.comparing(TimePeriodItem::getStartTime));
        this.notFillTreeSet = new TreeSet<>(Comparator.comparing(TimePeriodItem::getStartTime));
        
    }
    
    public void fill(LocalDateTime startTime, LocalDateTime endTime) {
        TimePoint point = this.buildPoint(startTime, endTime);
        if (point != null) {
            this.fillBitSet.set(point.getStart(), point.getEnd() + 1);
        }
    }
    
    private TimePoint buildPoint(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            // 结束时间大于开始时间的数据，丢弃
            return null;
        }
        
        // start <= MIN <= end <= MAX
        // 前面时间段有交集
        if (TimeUtils.isBeforeOrEqual(startTime, min) && TimeUtils.isBeforeOrEqual(min, endTime) && TimeUtils.isBeforeOrEqual(endTime, max)) {
            return TimePoint.builder()
                    .start(minPoint)
                    .end(TimePeriodConvertor.getPoint(this.min, endTime))
                    .build();
        }
        //  MIN <= start <= end <= MAX
        // 有交集
        if (TimeUtils.isBeforeOrEqual(min, startTime) && TimeUtils.isBeforeOrEqual(startTime, endTime) && TimeUtils.isBeforeOrEqual(endTime, max)) {
            return TimePoint.builder()
                    .start(TimePeriodConvertor.getPoint(this.min, startTime))
                    .end(TimePeriodConvertor.getPoint(this.min, endTime))
                    .build();
        }
        //  MIN <= start <= MAX <= end
        // 后面时间段有交集
        if (TimeUtils.isBeforeOrEqual(min, startTime) && TimeUtils.isBeforeOrEqual(startTime, max) && TimeUtils.isBeforeOrEqual(max, endTime)) {
            return TimePoint.builder()
                    .start(TimePeriodConvertor.getPoint(this.min, startTime))
                    .end(maxPoint)
                    .build();
        }
        // start <=  MIN <= MAX <= end
        // 有交集
        if (TimeUtils.isBeforeOrEqual(startTime, min) && TimeUtils.isBeforeOrEqual(min, max) && TimeUtils.isBeforeOrEqual(max, endTime)) {
            return TimePoint.builder()
                    .start(minPoint)
                    .end(maxPoint)
                    .build();
        }
        
        return null;
    }
    
    public Set<TimePeriodItem> getFillDateTimeSet() {
        this.buildFillTreeSet();
        return fillTreeSet;
    }
    
    public List<LocalDateTime> getFillDateTimeList() {
        Set<TimePeriodItem> timeSet = this.getFillDateTimeSet();
        return timeSet.stream()
                .flatMap(v -> Stream.of(v.getStartTime(), v.getEndTime()))
                .collect(Collectors.toList());
    }
    
    public Set<TimePeriodItem> getNotFillDateTimeSet() {
        this.buildNotFillTreeSet();
        return notFillTreeSet;
    }
    
    public Long getNotFillPointTotal() {
        Set<TimePeriodItem> notFillDateTimeSet = this.getNotFillDateTimeSet();
        if (ObjectUtils.isNotEmpty(notFillDateTimeSet)) {
            return notFillDateTimeSet.stream()
                    .map(item -> {
                        Duration between = Duration.between(item.getStartTime(), item.getEndTime());
                        return between.getSeconds() + 1;
                    }).reduce(0L, Long::sum);
        } else {
            return null;
        }
    }
    
    
    /**
     * BitSet to Set<TimePeriodItem>
     */
    private void buildFillTreeSet() {
        Set<TimePeriodItem> timePeriodItems = this.bitSetToTimePeriodItemSet(fillBitSet);
        fillTreeSet.clear();
        fillTreeSet.addAll(timePeriodItems);
    }
    
    /**
     * BitSet to Set<TimePeriodItem>
     *
     * @param bitSet bitSet
     * @return Set<TimePeriodItem>
     */
    private Set<TimePeriodItem> bitSetToTimePeriodItemSet(BitSet bitSet) {
        Set<TimePeriodItem> bitTreeSet = new TreeSet<>(Comparator.comparing(TimePeriodItem::getStartTime));
        int index = 0;
        while (true) {
            TimePeriodItem timePeriodItem = new TimePeriodItem();
            index = bitSet.nextSetBit(index);
            if (index == -1) {
                break;
            }
            timePeriodItem.setStartTime(TimePeriodConvertor.pointToLocalDateTime(min, index));
            index = bitSet.nextClearBit(index);
            timePeriodItem.setEndTime(TimePeriodConvertor.pointToLocalDateTime(min, index - 1));
            bitTreeSet.add(timePeriodItem);
        }
        return bitTreeSet;
    }
    
    /**
     * BitSet to Set<TimePeriodItem>
     */
    private void buildNotFillTreeSet() {
        BitSet notFillBitSet = BitSet.valueOf(fillBitSet.toByteArray());
        notFillBitSet.flip(minPoint, maxPoint + 1);
        Set<TimePeriodItem> timePeriodItems = this.bitSetToTimePeriodItemSet(notFillBitSet);
        notFillTreeSet.clear();
        notFillTreeSet.addAll(timePeriodItems);
    }
    
}
