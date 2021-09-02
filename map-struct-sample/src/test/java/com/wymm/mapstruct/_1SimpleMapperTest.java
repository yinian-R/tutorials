package com.wymm.mapstruct;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 最简单的示例，属性相同的两个 java bean 之间转换
 */
class _1SimpleMapperTest {
    
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    
    private final SimpleSourceDestinationMapper simpleSourceDestinationMapper = Mappers.getMapper(SimpleSourceDestinationMapper.class);
    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    
    /**
     * 属性相同的两个 java bean 之间转换
     */
    @Test
    void givenSourceToDestination_whenMaps_thenCorrect() {
        SimpleSource simpleSource = new SimpleSource();
        simpleSource.setName("SourceName");
        simpleSource.setDescription("SourceDescription");
        SimpleDestination destination = simpleSourceDestinationMapper.simpleSourceToSimpleDestination(simpleSource);
        
        assertEquals(simpleSource.getName(), destination.getName());
        assertEquals(simpleSource.getDescription(),
                destination.getDescription());
    }
    
    /**
     * 属性相同的两个 java bean 之间转换
     */
    @Test
    void givenDestinationToSource_whenMaps_thenCorrect() {
        SimpleDestination destination = new SimpleDestination();
        destination.setName("DestinationName");
        destination.setDescription("DestinationDescription");
        SimpleSource source = simpleSourceDestinationMapper.simpleDestinationToSimpleSource(destination);
        assertEquals(destination.getName(), source.getName());
        assertEquals(destination.getDescription(),
                source.getDescription());
    }
    
    /**
     * 1 属性不同的两个 java bean 之间转换
     * 2 属性 Bean 映射 Bean。如果 MapStruct 检测到需要转换的对象类型并且要转换的方法存在于同一个类中，它会自动使用它
     */
    @Test
    public void givenEmployeeDTOwithDiffNametoEmployee_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(1);
        dto.setEmployeeName("John");
        dto.setDivision(new DivisionDTO(1, "Division1"));
        
        Employee entity = employeeMapper.employeeDTOtoEmployee(dto);
        
        assertEquals(dto.getEmployeeId(), entity.getId());
        assertEquals(dto.getEmployeeName(), entity.getName());
        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }
    
    
    /**
     * Date 根据日期表达式转换为 String
     */
    @Test
    public void givenEmpStartDtMappingToEmpDTO_whenMaps_thenCorrect() throws ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());
        EmployeeDTO dto = employeeMapper.employeeToEmployeeDTO(entity);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        
        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(), entity.getStartDt().toString());
    }
    
    /**
     * String 根据日期表达式转换为 Date
     */
    @Test
    public void givenEmpDTOStartDtMappingToEmp_whenMaps_thenCorrect() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("01-04-2016 01:00:00");
        Employee entity = employeeMapper.employeeDTOtoEmployee(dto);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        
        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(), entity.getStartDt().toString());
    }
    
    /**
     * 以某种方式转换值
     * 在这种情况下，我们可以创建一个抽象类并实现我们想要自定义的方法，而将那些应该由 MapStruct 生成的方法保留为抽象。
     */
    @Test
    public void usingCustomMapperMethod() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setUuid(UUID.randomUUID().toString());
        transaction.setTotal(new BigDecimal(5));
    
        TransactionDTO dto = transactionMapper.toTransactionDTO(transaction);
    
        assertEquals(transaction.getUuid(), dto.getUuid());
        assertEquals(transaction.getTotal().multiply(new BigDecimal("100")).longValue(), dto.getTotalInCents());
    
        
        List<TransactionDTO> dtoList = transactionMapper.toTransactionDTO(Collections.singleton(transaction));
    
        assertEquals(dtoList.size(), 1);
    }
}