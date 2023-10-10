package managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import model.Client;
import model.Machine;
import model.Rent;
import org.apache.avalon.framework.parameters.ParameterException;
import repository.RentRepository;

public class RentManager {
    private RentRepository rentRepository;
    private EntityManager em;
    private EntityTransaction et;

    public RentManager(EntityManager entityManager) {
        em = entityManager;
        rentRepository = new RentRepository(entityManager);
    }

    public void removeRent(long ID) {
        et = em.getTransaction();
        et.begin();
        rentRepository.remove(rentRepository.getByID(ID));
        et.commit();
    }

    public List<Rent> findAll() {
        et = em.getTransaction();
        et.begin();
        List<Rent> list = rentRepository.findAll();
        et.commit();
        return list;
    }

    public Rent getRent(long ID) {
        et = em.getTransaction();
        et.begin();
        Rent rent = rentRepository.getByID(ID);
        et.commit();
        return rent;
    }

    public Rent addRent(Client client, Machine machine) throws ParameterException {
        Rent rent;
        if(client.getActiveRents() <= 0) {
            throw new ParameterException("Client with ID: " + client.getID() + " has no available rents left");
        }
        else {
            rent = new Rent(client, machine);
            et = em.getTransaction();
            et.begin();
            rentRepository.add(rent);
            et.commit();
            client.setActiveRents(client.getActiveRents() - 1);
        }
        return rent;
    }
}
