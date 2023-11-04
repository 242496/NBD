package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import java.util.List;
import java.util.UUID;
import model.Rent;

public class RentRepository extends Repository<Rent> {
    public RentRepository(EntityManager entityManager) { super(entityManager); }

    @Override
    public List<Rent> findAll() {
        List<Rent> list;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> query = cb.createQuery(Rent.class);
        From<Rent,Rent> from = query.from(Rent.class);
        query.select(from);
        list = em.createQuery(query).getResultList();
        return list;
    }

    @Override
    public Rent getByID(UUID ID) {
        return em.find(Rent.class, ID);
    }
}
