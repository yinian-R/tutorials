package com.wymm.springrestquerylanguage.repository;

import com.wymm.springrestquerylanguage.model.SearchCriteria;
import com.wymm.springrestquerylanguage.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
@Repository
public class UserDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * 接受非常简单的约束的列表，根据这些约束组成查询，进行搜索并返回结果
     *
     * @param params
     * @return
     */
    public List<User> searchUser(List<SearchCriteria> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        
        Predicate predicate = builder.conjunction();
        
        UserSearchQueryCriteriaConsumer searchConsumer = new UserSearchQueryCriteriaConsumer(predicate, builder, from);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);
        
        List<User> result = entityManager.createQuery(query).getResultList();
        return result;
    }
    
    public void save(User entity) {
        entityManager.persist(entity);
    }
}
