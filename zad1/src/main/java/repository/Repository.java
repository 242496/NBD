package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public abstract class Repository<T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
    EntityManager em = emf.createEntityManager();

    public Repository(EntityManager entityManager) {
        em = entityManager;
    }

    public void add(T object) {
        if(em.contains(object)) {
            em.merge(object);
        } else {
            em.persist(object);
        }
    }

    public void remove(T object) {
        if(em.contains(object)){
            em.remove(object);
        }
    }

    public abstract List<T> findAll();
    public abstract T getByID(UUID ID);
}
