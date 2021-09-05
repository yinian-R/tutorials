package com.wymm.mapstruct.simple;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper {
    @Mappings({
            @Mapping(target = "employeeId", source = "source.id"),
            @Mapping(target = "employeeName", source = "source.name"),
            @Mapping(target="employeeStartDt", source = "source.startDt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee source);
    
    @Mappings({
            @Mapping(target = "id", source = "source.employeeId"),
            @Mapping(target = "name", source = "source.employeeName"),
            @Mapping(target="startDt", source = "source.employeeStartDt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    Employee employeeDTOtoEmployee(EmployeeDTO source);
    
    /**
     * 如果 MapStruct 检测到需要转换的对象类型并且要转换的方法存在于同一个类中，它会自动使用它
     */
    DivisionDTO divisionToDivisionDTO(Division source);
    Division divisionDTOtoDivision(DivisionDTO source);
}
