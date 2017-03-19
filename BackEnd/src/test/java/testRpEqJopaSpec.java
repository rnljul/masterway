import org.junit.Test;
import ru.fancytraffic.masterway.domain.RoutePassport;
import ru.fancytraffic.masterway.repository.RoutePassportRepository;
import ru.fancytraffic.masterway.specification.Specification;
import ru.fancytraffic.masterway.specification.SpecificationRoutePasspotEqJopa;

import java.util.Set;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 19.03.2017.
 */
public class testRpEqJopaSpec {
    @Test
    public void whenJopaSpecThenJopa(){
        RoutePassportRepository rep = new RoutePassportRepository("MASTERWAY");
        Specification<RoutePassport> rpEqJopa = new SpecificationRoutePasspotEqJopa();
        Set<RoutePassport> jopas = rep.get(rpEqJopa.and(rpEqJopa));
        boolean isJopa = false;
        jopas.forEach(jopa -> assertEquals(jopa.getName(), "jopa"));
    }
}
