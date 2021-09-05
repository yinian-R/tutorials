package com.wymm.mapstruct;

import com.wymm.mapstruct.custom.UserBodyImperialValuesDTO;
import com.wymm.mapstruct.custom.UserBodyValues;
import com.wymm.mapstruct.custom.UserBodyValuesMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 自定义属性转换方法
 */
public class _3CustomMapperTest {
    
    /**
     * 通过在 qualifiedByName 属性中键入自定义方法来调用自定义方法
     * 也可以 qualifiedBy 键入自定义注释来调用自定义方法
     */
    @Test
    void withQualifiedByName_whenMaps_thenCorrect() {
        UserBodyImperialValuesDTO dto = new UserBodyImperialValuesDTO();
        dto.setInch(10);
        
        UserBodyValues obj = UserBodyValuesMapper.INSTANCE.userBodyValuesMapper(dto);
        
        assertNotNull(obj);
        assertEquals(25.4, obj.getCentimeter(), 0);
    }
}
