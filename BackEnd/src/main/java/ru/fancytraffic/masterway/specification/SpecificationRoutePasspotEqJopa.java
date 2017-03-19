package ru.fancytraffic.masterway.specification;

import ru.fancytraffic.masterway.domain.RoutePassport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 19.03.2017.
 */
public class SpecificationRoutePasspotEqJopa extends AbstractSpecification<RoutePassport> {

    @Override
    public boolean isSatisfiedBy(RoutePassport candidate) {
        return candidate.getName() == "jopa";
    }

    @Override
    public Predicate toPredicate(Root<RoutePassport> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("name"), "jopa");
    }
}
