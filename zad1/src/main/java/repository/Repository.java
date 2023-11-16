package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

public abstract class Repository<T> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public Repository(EntityManager entityManager) {
        em = entityManager;
    }

    public void add(T object) {
        if(em.contains(object)) {
            et.begin();
            em.merge(object);
            et.commit();
        } else {
            et.begin();
            em.persist(object);
            et.commit();
        }
    }

    public void remove(T object) {
        if(em.contains(object)){
            et.begin();
            em.remove(object);
            et.commit();
        }
    }

    public abstract List<T> findAll();
    public abstract T getByID(UUID ID);
}
