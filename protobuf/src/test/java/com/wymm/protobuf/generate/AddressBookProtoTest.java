package com.wymm.protobuf.generate;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 需先使用 protobuf 生成 AddressBookProto.java
 */
class AddressBookProtoTest {
    
    private String filePath = "target/address_book";
    
    /**
     * 使用protobuf创建实例
     */
    @Test
    public void createModel() {
        AddressBookProto.Person person = AddressBookProto.Person.newBuilder()
                .setId("1")
                .setName("join")
                .setEmail("4545@qq.com")
                .addNumbers("123")
                .addNumbers("456")
                .build();
        
        assertEquals(person.getId(), "1");
        assertEquals(person.getName(), "join");
        assertEquals(person.getEmail(), "4545@qq.com");
        assertEquals(person.getNumbersCount(), 2);
    }
    
    /**
     * 序列化数据
     */
    @Test
    public void getAddressBook_thenSaveAsFile() throws IOException {
        AddressBookProto.Person person = AddressBookProto.Person.newBuilder()
                .setId("1")
                .setName("join")
                .setEmail("4545@qq.com")
                .addNumbers("123")
                .addNumbers("456")
                .build();
        
        AddressBookProto.AddressBook addressBook = AddressBookProto.AddressBook.newBuilder()
                .addPerson(person)
                .build();
        
        FileOutputStream fos = new FileOutputStream(filePath);
        addressBook.writeTo(fos);
    }
    
    /**
     * 反序列化数据
     */
    @Test
    public void getFile_thenTransformAddressBook() throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        
        AddressBookProto.AddressBook addressBook = AddressBookProto.AddressBook.newBuilder().mergeFrom(fis).build();
        assertEquals(addressBook.getPerson(0).getId(), "1");
        assertEquals(addressBook.getPerson(0).getName(), "join");
        assertEquals(addressBook.getPerson(0).getEmail(), "4545@qq.com");
        assertEquals(addressBook.getPerson(0).getNumbersCount(), 2);
    }
    
}