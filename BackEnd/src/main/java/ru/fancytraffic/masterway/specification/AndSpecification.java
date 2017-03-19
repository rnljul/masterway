package ru.fancytraffic.masterway.specification;

import ru.fancytraffic.masterway.domain.RepositoryEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 18.03.2017.
 */
public class AndSpecification<T extends RepositoryEntity<?>>
        extends AbstractSpecification<T> {

    protected Specification<T> left;
    protected Specification<T> right;

    public AndSpecification(Specification<T> left, Specification<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(T candidate) {
        return left.isSatisfiedBy(candidate) && right.isSatisfiedBy(candidate);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(left.toPredicate(root, criteriaBuilder), right.toPredicate(root, criteriaBuilder));
    }
}
