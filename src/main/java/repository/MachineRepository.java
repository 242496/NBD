package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import java.util.List;
import model.Machine;

public class MachineRepository extends Repository<Machine> {
    public MachineRepository(EntityManager entityManager) { super(entityManager); }

    @Override
    public List<Machine> findAll() {
        List<Machine> list;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Machine> query = cb.createQuery(Machine.class);
        From<Machine,Machine> from = query.from(Machine.class);
        query.select(from);
        list = em.createQuery(query).getResultList();
        return list;
    }

    @Override
    public Machine getByID(long ID) {
        return em.find(Machine.class, ID);
    }
}
