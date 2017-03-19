package ru.fancytraffic.masterway.domain;


import javax.persistence.Entity;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
@Entity
public class RoutePassport extends RepositoryEntity<Integer> {

    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
