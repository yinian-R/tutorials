package com.wymm.springdatajpa.repository;

import com.wymm.springdatajpa.entity.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    List<Person> findPersonByLastName(@Param("name") String name);

}