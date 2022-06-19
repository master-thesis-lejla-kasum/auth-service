package com.master.authservice.repository.institution;

import com.master.authservice.dto.InstitutionSearchRequest;
import com.master.authservice.model.InstitutionEntity;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class InstitutionCustomRepositoryImpl implements InstitutionCustomRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<InstitutionEntity> search(InstitutionSearchRequest request) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<InstitutionEntity> query = cb.createQuery(InstitutionEntity.class);
        Root<InstitutionEntity> resource = query.from(InstitutionEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.getApproved() != null) {
            predicates.add(cb.equal(resource.get("approved"), request.getApproved()));
        }

        if (StringUtils.hasText(request.getName())) {
            predicates.add(cb.like(cb.lower(resource.get("name")), "%" + request.getName().toLowerCase() + "%"));
        }

        query
                .select(resource)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query).getResultList();
    }
}
