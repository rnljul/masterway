package ru.fancytraffic.masterway.specification;

import ru.fancytraffic.masterway.domain.RepositoryEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 12.03.2017.
 */
public interface Specification<T extends RepositoryEntity<?>> {
    boolean isSatisfiedBy(T candidate);

    Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder);

    Specification<T> and(Specification<T> other);

    Specification<T> or(Specification<T> other);

    Specification<T> not(Specification<T> other);
}
