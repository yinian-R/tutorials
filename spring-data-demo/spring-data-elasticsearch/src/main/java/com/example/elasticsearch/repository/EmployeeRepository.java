package com.example.elasticsearch.repository;

import com.example.elasticsearch.modal.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, Long> {

    List<Employee> findByName(String name);
}
