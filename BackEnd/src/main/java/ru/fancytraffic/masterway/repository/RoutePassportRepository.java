package ru.fancytraffic.masterway.repository;

import ru.fancytraffic.masterway.domain.RoutePassport;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class RoutePassportRepository extends JpaRepository<Integer, RoutePassport> {
    public RoutePassportRepository(String persistenceUnitName) {
        super(RoutePassport.class, persistenceUnitName);
    }
}
