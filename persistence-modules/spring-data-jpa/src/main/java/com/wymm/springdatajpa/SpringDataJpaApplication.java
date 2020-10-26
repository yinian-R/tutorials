package com.wymm.springdatajpa;

import com.wymm.springdatajpa.entity.Customer;
import com.wymm.springdatajpa.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaApplication {
    
    private static final Logger log = LoggerFactory.getLogger(SpringDataJpaApplication.class);
    
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner demo(CustomerRepository repository) {
        return (args -> {
            
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));
            
            log.info("Customer found with save() finish");
            
            log.info("Customer found with findAll()");
            log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");
            
            repository.findById(1L).ifPresent(customer -> {
                log.info("Customer found with findById()");
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                log.info(customer.toString());
                log.info("");
            });
            
            log.info("Customer found with findByLastName('findByLastName')");
            log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            repository.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");
            
        });
    }
    
}
