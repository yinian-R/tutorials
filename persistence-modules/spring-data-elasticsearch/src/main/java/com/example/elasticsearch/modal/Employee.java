package com.example.elasticsearch.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "sample", type = "employee")
public class Employee {

    @Id
    private Long id;
    private String name;
    private int age;
    private String position;
}
