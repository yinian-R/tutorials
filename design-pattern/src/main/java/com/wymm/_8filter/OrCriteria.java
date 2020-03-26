package com.wymm._8filter;

import java.util.List;
import java.util.stream.Collectors;

public class OrCriteria implements Criteria {
    private Criteria criteria;
    private Criteria otherCriteria;
    
    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }
    
    @Override
    public List<Person> meetCriteria(List<Person> persons) {
        List<Person> firstPersonItems = criteria.meetCriteria(persons);
        List<Person> otherPersonItems = otherCriteria.meetCriteria(persons);
        firstPersonItems.addAll(otherPersonItems);
        return firstPersonItems.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
