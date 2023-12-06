package managers;

import domain.Client;
import domain.Machine;
import domain.Rent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import repository.domain.RentRepository;
import repository.mongo.RentMongoRepository;
import repository.redis.RentRedisRepository;

public class RentManager {
    private RentRepository rentRepository;
    private ClientManager clientManager;
    private MachineManager machineManager;

    public RentManager(ClientManager clientManager, MachineManager machineManager) {
        this.rentRepository = new RentRepository(new RentMongoRepository(), new RentRedisRepository());
        this.clientManager = clientManager;
        this.machineManager = machineManager;
    }

    public Rent addRent(Client client, Machine machine) throws Exception {
        Rent rent;
        if(client.getActiveRents() >= client.getType().getMaxRents()) {
            throw new Exception("Client with ID: " + client.getEntityId() + " has no available rents left");
        } else if (machine.isRented()) {
            throw new Exception("Machine with ID: " + machine.getEntityId() + " is already rented");
        } else {
            rent = new Rent(client, machine);
            rentRepository.add(rent);
            client.setActiveRents(client.getActiveRents()+1);
            machine.setRented(true);
            return rent;
        }
    }

    public void removeRent(UUID id) throws Exception {
        getRent(id).getClient().setActiveRents(getRent(id).getClient().getActiveRents()-1);
        getRent(id).getMachine().setRented(false);
        rentRepository.remove(id);
    }

    public List<Rent> findAll() throws Exception {
        List<Rent> list = new ArrayList<Rent>();
        rentRepository.findAll().stream().forEach(rent -> list.add(rent));
        return list;
    }

    public Rent getRent(UUID ID) throws Exception {
        return rentRepository.getById(ID);
    }
}
