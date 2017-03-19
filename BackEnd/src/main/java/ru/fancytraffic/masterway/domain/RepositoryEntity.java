package ru.fancytraffic.masterway.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
@MappedSuperclass
public abstract class RepositoryEntity<T> {
    @Id
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryEntity identity = (RepositoryEntity) o;
        return Objects.equals(id, identity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toString());
    }

    @Override
    public String toString() {
        return "JpaEntity{" +
                "id=" + id +
                '}';
    }
}
