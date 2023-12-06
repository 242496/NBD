package repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T> extends AutoCloseable {
    T add(T entity) throws Exception;
    T getById(UUID id) throws Exception;
    void remove(UUID id);
    T update(T entity) throws Exception;
    long size() throws Exception;
    List<T> findAll() throws Exception;
}
