package com.spaco_apoio.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Repository <T>{

    protected EntityManager entityManager;

    public abstract Class<T> getClazz();

    protected CriteriaBuilder builder(){
        return entityManager.getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCriteria(){
        return builder().createQuery(getClazz());
    }

    @Autowired
    protected void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    protected String like(String param){
        return "%".concat(param).concat("%");
    }
}
