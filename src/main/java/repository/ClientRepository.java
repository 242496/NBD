package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import java.util.List;
import model.Client;

public class ClientRepository extends Repository<Client> {

    public ClientRepository(EntityManager entityManager) {
        super(entityManager);
    }
    @Override
    public List<Client> findAll() {
        List<Client> list;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> query = cb.createQuery(Client.class);
        From<Client,Client> from = query.from(Client.class);
        query.select(from);
        list = em.createQuery(query).getResultList();

        return list;
    }

    @Override
    public Client getByID(long ID) {
        return em.find(Client.class, ID);
    }
}
