package ru.fancytraffic.masterway.specification;

import ru.fancytraffic.masterway.domain.RepositoryEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 19.03.2017.
 */
public class NotSpecification<T extends RepositoryEntity<?>>
        extends AbstractSpecification<T> {
    protected Specification<T> left;

    public NotSpecification(Specification<T> left) {
        this.left = left;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !left.isSatisfiedBy(candidate);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.not(left.toPredicate(root, criteriaBuilder));
    }
}
