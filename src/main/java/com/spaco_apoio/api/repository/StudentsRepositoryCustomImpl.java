package com.spaco_apoio.api.repository;

import com.spaco_apoio.api.model.Students;
import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.rest.params.StudentsParams;
import com.spaco_apoio.api.utility.UtilSecurity;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class StudentsRepositoryCustomImpl extends Repository<Students> implements StudentsRepositoryCustom{


    public Page<Students> list(StudentsParams params, Pageable pageable){
        CriteriaQuery<Students> criteria = getCriteria();
        CriteriaQuery<Long> count = builder().createQuery(Long.class);
        Root<Students> root = criteria.from(getClazz());
        Root<Students> countRoot = count.from(getClazz());
        Collection<Predicate> predicates = new ArrayList<>();
        Collection<Predicate> countpredicates = new ArrayList<>();

        Join<Students, Users> joinUsers = root.join("user");
        Join<Users, UsersStatus> joinStatus = joinUsers.join("status");
        Join<Students, Users> joinCountUsers = countRoot.join("user");
        Join<Users, UsersStatus> joinCountStatus = joinCountUsers.join("status");

        Path<Long> id = root.get("id");
        Path<String> name = joinUsers.get("name");
        Path<LocalDate> startDate = joinUsers.get("startDate");
        Path<LocalDate> endDate = joinUsers.get("endDate");
        Path<String> statusDescription = joinStatus.get("description");

        criteria.multiselect(id, name, startDate, endDate, statusDescription);

        if(StringUtils.hasText(params.getName())){
            predicates.add(builder().like(builder().upper(name), like(params.getName().toUpperCase())));
            countpredicates.add(builder().like(builder().upper(joinCountUsers.get("name")), like(params.getName().toUpperCase())));
        }
        if(Objects.nonNull(params.getStartDate())){
            predicates.add(builder().greaterThanOrEqualTo(startDate, params.getStartDate()));
            countpredicates.add(builder().greaterThanOrEqualTo(joinCountUsers.get("startDate"), params.getStartDate()));
        }
        if(Objects.nonNull(params.getEndDate())){
            predicates.add(builder().lessThanOrEqualTo(endDate, params.getEndDate()));
            countpredicates.add(builder().lessThanOrEqualTo(joinCountUsers.get("endDate"), params.getEndDate()));
        }
        if(StringUtils.hasText(params.getStatus())){
            predicates.add(builder().equal(joinStatus.get("id"), UtilSecurity.decryptId(params.getStatus())));
            countpredicates.add(builder().equal(joinCountStatus.get("id"), UtilSecurity.decryptId(params.getStatus())));
        }

        count.select(builder().count(countRoot.get("id")));
        count.where(countpredicates.toArray(new Predicate[0]));
        criteria.where(predicates.toArray(new Predicate[0]));
        criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder()));
        List<Students> studentsList = entityManager
                .createQuery(criteria)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .getResultList();
        Long counts = entityManager.createQuery(count).getSingleResult();
        return new PageImpl<>(studentsList, pageable, counts);
    }

    public Students details(Long id){
        CriteriaQuery<Students> criteria = getCriteria();
        Root<Students> root = criteria.from(getClazz());

        Join<Students, Users> joinUser = root.join("user");

        criteria.multiselect(
                root.get("id"),
                root.get("comments"),
                root.get("birthDate"),
                joinUser.get("name"),
                joinUser.get("email"),
                joinUser.get("cpf"),
                joinUser.get("startDate"),
                joinUser.get("endDate"),
                joinUser.get("statusId")
        );

        criteria.where(builder().equal(root.get("id"), id));

        return entityManager.createQuery(criteria).getSingleResult();
    }

    @Override
    public Class<Students> getClazz() {
        return Students.class;
    }
}
