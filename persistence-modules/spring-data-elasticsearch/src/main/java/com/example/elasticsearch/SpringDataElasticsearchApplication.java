package com.example.elasticsearch;

import com.example.elasticsearch.modal.Employee;
import com.example.elasticsearch.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class SpringDataElasticsearchApplication implements CommandLineRunner {
    
    @Autowired
    EmployeeRepository employeeRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringDataElasticsearchApplication.class, args);
    }
    
    @Bean
    public SampleDataSet dataSet() {
        return new SampleDataSet();
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        for (Employee employee : employeeRepository.findAll()) {
            System.out.println(employee);
        }
    }
}
