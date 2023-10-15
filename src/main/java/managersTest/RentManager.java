package managersTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;
import java.util.List;
import model.Client;
import model.Machine;
import model.Rent;
import org.apache.avalon.framework.parameters.ParameterException;
import repository.RentRepository;

public class RentManager {
    private final RentRepository rentRepository;
    private final EntityManager em;
    private EntityTransaction et;

    public RentManager(EntityManager entityManager) {
        em = entityManager;
        rentRepository = new RentRepository(entityManager);
    }

    public void removeRent(long ID) {
        et = em.getTransaction();
        et.begin();
        Rent rent = rentRepository.getByID(ID);
        rent.getClient().setActiveRents(rent.getClient().getActiveRents()-1);
        rent.getMachine().setRented(false);
        rentRepository.remove(rent);
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

    public Rent addRent(Client client, Machine machine) throws OptimisticLockException, ParameterException {
        Rent rent;
        if(client.getActiveRents() >= client.getType().getMaxRents()) {
            throw new OptimisticLockException("Client with ID: " + client.getID() + " has no available rents left");
        } else if (machine.isRented()) {
            throw new OptimisticLockException("Machine with ID: " + machine.getID() + " is already rented");
        } else {
            rent = new Rent(client, machine);
            et = em.getTransaction();
            et.begin();
            rentRepository.add(rent);
            client.setActiveRents(client.getActiveRents()+1);
            machine.setRented(true);
            et.commit();
        }
        return rent;
    }
}
