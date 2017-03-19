package ru.fancytraffic.masterway.specification;

import ru.fancytraffic.masterway.domain.RepositoryEntity;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 18.03.2017.
 */
public abstract class AbstractSpecification<T extends RepositoryEntity<?>> implements Specification<T> {

    @Override
    public Specification<T> and(Specification<T> other) {
        return new AndSpecification<>(this, other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return new OrSpecification<>(this, other);
    }

    @Override
    public Specification<T> not(Specification<T> other) {
        return new NotSpecification<>(this);
    }
}
