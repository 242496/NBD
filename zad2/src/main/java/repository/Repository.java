package repository;

import java.util.List;
import java.util.UUID;
import model.UniqueId;

public interface Repository<T> {
    T add(T entity);
    T getById(UniqueId id);
    void remove(UniqueId id);
    void update(T entity);
    long size();
    List<T> findAll();
}
