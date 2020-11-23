package socialnetwork.repository.factory;

import socialnetwork.domain.Entity;

import java.time.LocalDate;

public abstract class Factory <ID, E extends Entity<ID>> {
    protected String separator;

    public Factory(String separator) {
        this.separator = separator;
    }

    public abstract E extractEntity(String s);
    public abstract String createEntityAsString(E entity);
}
