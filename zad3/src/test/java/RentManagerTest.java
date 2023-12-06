import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.Client;
import domain.ClientType;
import domain.Intermediate;
import domain.Machine;
import domain.Rent;
import managers.ClientManager;
import managers.MachineManager;
import managers.RentManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.domain.RentRepository;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;
import repository.redis.RentRedisRepository;

public class RentManagerTest {
    private static RentManager rentManager;
    private static ClientManager clientManager = new ClientManager();
    private static MachineManager machineManager = new MachineManager();
    private static ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
    private static MachineMongoRepository machineMongoRepository = new MachineMongoRepository();

    private static RentMongoRepository rentMongoRepository = new RentMongoRepository();
    private static RentRedisRepository rentRedisRepository = new RentRedisRepository();
    private static RentRepository rentRepository = new RentRepository(rentMongoRepository, rentRedisRepository);

    @BeforeEach
    public void beforeEach() {
        rentManager = new RentManager(clientManager, machineManager);
        rentRedisRepository.clearCache();
    }

    @Test
    public void CreateTest() throws Exception {
        ClientType intermediate = new Intermediate();
        Client client = new Client("Aron", intermediate);
        Machine machine = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        assertEquals(client.getActiveRents(), 0);
        assertEquals(machine.isRented(), false);
        Rent rent = rentManager.addRent(client, machine);
        assertEquals(rentManager.getRent(rent.getEntityId()).getClient().getUsername(), client.getUsername());
        assertEquals(client.getActiveRents(), 1);
        assertEquals(machine.isRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        Machine machine1 = new Machine(3, 256, 300, Machine.SystemType.WINDOWS7);
        Machine machine2 = new Machine(3, 256, 500, Machine.SystemType.FEDORA);
        Rent rent1 = rentManager.addRent(client, machine1);
        rentRepository.update(rent);
        rentRepository.update(rent1);
        assertThrows(Exception.class, () ->{Rent rent2 = rentManager.addRent(client, machine2);});
        Client client1 = new Client("Storm", intermediate);
        assertThrows(Exception.class, () ->{Rent rent2 = rentManager.addRent(client1, machine1);});
        rentManager.removeRent(rent.getEntityId());
        rentManager.removeRent(rent1.getEntityId());
    }

    @Test
    public void ReadTest() throws Exception {
        ClientType intermediate = new Intermediate();
        Client client = new Client("Aron", intermediate);
        Machine machine = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        assertEquals(client.getActiveRents(), 0);
        assertEquals(machine.isRented(), false);
        Rent rent = rentManager.addRent(client, machine);
        assertEquals(rentManager.getRent(rent.getEntityId()).getClient().getUsername(), client.getUsername());
        assertEquals(client.getActiveRents(), 1);
        assertEquals(machine.isRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        Machine machine1 = new Machine(3, 256, 300, Machine.SystemType.WINDOWS7);
        Machine machine2 = new Machine(3, 256, 500, Machine.SystemType.FEDORA);
        Rent rent1 = rentManager.addRent(client, machine1);
        rentRepository.update(rent);
        rentRepository.update(rent1);
        assertEquals(rentRepository.findAll().size(), 2);
        assertEquals(rentRepository.getById(rent1.getEntityId()).getClient().getUsername(), client.getUsername());
        rentManager.removeRent(rent.getEntityId());
    }

    @Test
    public void DeleteTest() throws Exception {
        ClientType intermediate = new Intermediate();
        Client client = new Client("Aron", intermediate);
        Machine machine = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        assertEquals(client.getActiveRents(), 0);
        assertEquals(machine.isRented(), false);
        Rent rent = rentManager.addRent(client, machine);
        assertEquals(rentManager.getRent(rent.getEntityId()).getClient().getUsername(), client.getUsername());
        assertEquals(client.getActiveRents(), 1);
        assertEquals(machine.isRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        Machine machine1 = new Machine(3, 256, 300, Machine.SystemType.WINDOWS7);
        Machine machine2 = new Machine(3, 256, 500, Machine.SystemType.FEDORA);
        Rent rent1 = rentManager.addRent(client, machine1);
        assertEquals(rentManager.findAll().size(), 2);
        assertEquals(machine.isRented(), true);
        assertEquals(machine1.isRented(), true);
        rentManager.removeRent(rent1.getEntityId());
        assertEquals(rentManager.findAll().size(), 1);
        assertEquals(machine.isRented(), true);
//        assertEquals(machine1.isRented(), false);
//        rentManager.removeRent(rent.getEntityId());
    }
}
