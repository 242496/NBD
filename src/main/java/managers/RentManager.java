package managers;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import model.Client;
import model.Machine;
import model.Rent;
import repository.RentRepository;

public class RentManager {
    private final RentRepository rentRepository;
    private final EntityManager em;

    public RentManager(EntityManager entityManager) {
        em = entityManager;
        rentRepository = new RentRepository(entityManager);
    }

    public void removeRent(UUID ID) {
        Rent rent = rentRepository.getByID(ID);
        rent.getClient().setActiveRents(rent.getClient().getActiveRents()-1);
        rent.getMachine().setRented(false);
        rentRepository.remove(rent);
    }

    public List<Rent> findAll() {
        List<Rent> list = rentRepository.findAll();
        return list;
    }

    public Rent getRent(UUID ID) {
        Rent rent = rentRepository.getByID(ID);
        return rent;
    }

    public Rent addRent(Client client, Machine machine) throws Exception {
        Rent rent;
        if(client.getActiveRents() >= client.getType().getMaxRents()) {
            throw new Exception("Client with ID: " + client.getID() + " has no available rents left");
        } else if (machine.isRented()) {
            throw new Exception("Machine with ID: " + machine.getID() + " is already rented");
        } else {
            rent = new Rent(client, machine);
            rentRepository.add(rent);
            client.setActiveRents(client.getActiveRents()+1);
            machine.setRented(true);
        }
        return rent;
    }
}
