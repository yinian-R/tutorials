package com.wymm.jackson.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private List<String> phoneNumbers = new ArrayList<>();
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Address> address = new ArrayList<>();
    
    public boolean addPhoneNumber(String phoneNumber){
        return phoneNumbers.add(phoneNumber);
    }
    public boolean addAddress(Address address){
        return this.address.add(address);
    }
}
