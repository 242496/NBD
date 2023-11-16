package managers;

import domain.Client;
import domain.Rent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mapper.RentMapper;
import model.ClientMgd;
import model.MachineMgd;
import model.RentMgd;
import model.UniqueId;
import repository.ClientRepository;
import repository.MachineRepository;
import repository.RentRepository;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;

public class RentManager {
    private RentRepository rentRepository;
    private ClientRepository clientRepository;
    private MachineRepository machineRepository;
    private RentMapper rentMapper;

    public RentManager() {
        this.rentRepository = new RentRepository(new RentMongoRepository());
        this.clientRepository = new ClientRepository(new ClientMongoRepository());
        this.machineRepository = new MachineRepository(new MachineMongoRepository());
        this.rentMapper = new RentMapper();
    }

    public RentMgd addRent(ClientMgd client, MachineMgd machine) throws Exception {
        RentMgd rent;
        if(client.getActiveRents() >= client.getType().getMaxRents()) {
            throw new Exception("Client with ID: " + client.getEntityId() + " has no available rents left");
        } else if (machine.getIsRented()) {
            throw new Exception("Machine with ID: " + machine.getEntityId() + " is already rented");
        } else {
            rent = new RentMgd(client, machine);
            rentRepository.add(rent);
            client.setActiveRents(client.getActiveRents()+1);
            machine.setIsRented(true);
            return rent;
        }
    }

    public void removeRent(UniqueId ID) throws Exception {
        RentMgd rent = rentRepository.getById(ID);
        rent.getClient().setActiveRents(rent.getClient().getActiveRents()-1);
        rent.getMachine().setIsRented(false);
        rentRepository.remove(ID);
    }

    public List<Rent> findAll() throws Exception {
        List<RentMgd> rentsList = rentRepository.findAll();
        List<Rent> list = new ArrayList<Rent>();
        for(RentMgd rent :rentsList) {
            list.add(rentMapper.toDomainModel(rent));
        }
        return list;
    }

    public Rent getRent(UniqueId ID) throws Exception {
        RentMgd rent = rentRepository.getById(ID);
        return rentMapper.toDomainModel(rent);
    }

//    public RentMgd getRentByClient(String Username){
//        return rentRepository.findByClient(clientRepository.getByUsername(Username));
//    }
}
