package ru.fancytraffic.masterway.server;


import ru.fancytraffic.masterway.domain.RoutePassport;
import ru.fancytraffic.masterway.repository.RoutePassportRepository;
import ru.fancytraffic.masterway.specification.Specification;
import ru.fancytraffic.masterway.specification.SpecificationRoutePasspotEqJopa;


import java.util.Set;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class HibTest {




    public static void main(String[] args) {
        RoutePassportRepository rep = new RoutePassportRepository("MASTERWAY");

        Specification<RoutePassport> rpEqJopa = new SpecificationRoutePasspotEqJopa();
        Set<RoutePassport> jopas = rep.get(rpEqJopa.and(rpEqJopa));
        jopas.forEach(jopa -> System.out.println(jopa.toString()));

    }
}
