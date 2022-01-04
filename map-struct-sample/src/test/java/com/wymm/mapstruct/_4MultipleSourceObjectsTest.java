package com.wymm.mapstruct;

import com.wymm.mapstruct.multiple.Address;
import com.wymm.mapstruct.multiple.Customer;
import com.wymm.mapstruct.multiple.DeliveryAddress;
import com.wymm.mapstruct.multiple.DeliveryAddressMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 支持多个数据源
 */
public class _4MultipleSourceObjectsTest {
    
    /**
     * 支持多个数据源
     */
    @Test
    void withMultipleSourceObjects_whenMaps_thenCorrect() {
        // given a customer
        Customer customer = new Customer();
        customer.setFirstName("Max");
        customer.setLastName("Powers");
        
        // and some address
        Address homeAddress = new Address();
        homeAddress.setStreet("123 Some Street");
        homeAddress.setCounty("Nevada");
        homeAddress.setPostalcode("89123");
        
        // when calling DeliveryAddressMapper::from
        DeliveryAddress deliveryAddress = DeliveryAddressMapper.INSTANTS.from(customer, homeAddress);
        
        // then a new DeliveryAddress is created, based on the given customer and his home address
        assertEquals(deliveryAddress.getForename(), customer.getFirstName());
        assertEquals(deliveryAddress.getSurname(), customer.getLastName());
        assertEquals(deliveryAddress.getStreet(), homeAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), homeAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), homeAddress.getPostalcode());
    }
    
    /**
     * 使用 @MappingTarget 更新现有对象
     * 更新所有能对应上的属性
     */
    @Test
    void withMappingTarget_whenMaps_thenCorrect() {
        // given a delivery address
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setForename("Max");
        deliveryAddress.setSurname("Powers");
        deliveryAddress.setStreet("123 Some Street");
        deliveryAddress.setCounty("Nevada");
        deliveryAddress.setPostalcode("89123");
        
        // and some new address
        Address newAddress = new Address();
        newAddress.setStreet("456 Some other street");
        newAddress.setCounty("Arizona");
        newAddress.setPostalcode("12345");
        
        // when calling DeliveryAddressMapper::updateAddress
        DeliveryAddress updatedDeliveryAddress = DeliveryAddressMapper.INSTANTS.updateAddress(deliveryAddress, newAddress);
        
        // then the *existing* delivery address is updated
        assertSame(deliveryAddress, updatedDeliveryAddress);
        
        assertEquals(deliveryAddress.getStreet(), newAddress.getStreet());
        assertEquals(deliveryAddress.getCounty(), newAddress.getCounty());
        assertEquals(deliveryAddress.getPostalcode(), newAddress.getPostalcode());
    }
}
