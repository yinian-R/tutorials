package com.example.elasticsearch;

import com.example.elasticsearch.modal.Employee;
import com.example.elasticsearch.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SampleDataSet {
    private static final String INDEX_NAME = "sample";
    private static final String INDEX_TYPE = "employee";
    
    @Autowired
    EmployeeRepository repository;
    
    @Autowired
    ElasticsearchTemplate template;
    
    @PostConstruct
    public void init() {
        bulk();
    }
    
    private void bulk() {
        try {
            if (!template.indexExists(INDEX_NAME)) {
                template.createIndex(INDEX_NAME);
            }
            ObjectMapper mapper = new ObjectMapper();
            List<IndexQuery> queries = new ArrayList<>();
            for (Employee employee : employees()) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(employee.getId().toString());
                indexQuery.setSource(mapper.writeValueAsString(employee));
                indexQuery.setIndexName(INDEX_NAME);
                indexQuery.setType(INDEX_TYPE);
                queries.add(indexQuery);
            }
            if (queries.size() > 0) {
                template.bulkIndex(queries);
            }
            template.refresh(INDEX_NAME);
            log.info("BulkIndex completed: {}");
        } catch (Exception e) {
            log.error("Error bulk index", e);
        }
    }
    
    private List<Employee> employees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "yinian", 1, "guangzhou"));
        employees.add(new Employee(2L, "yinian2", 2, "guangzhou"));
        employees.add(new Employee(3L, "yinian3", 3, "guangzhou"));
        employees.add(new Employee(4L, "yinian4", 4, "guangzhou"));
        employees.add(new Employee(5L, "yinian5", 5, "guangzhou"));
        return employees;
    }
}
