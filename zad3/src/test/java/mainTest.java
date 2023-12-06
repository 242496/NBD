import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.Advanced;
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
import org.junit.jupiter.api.TestInstance;
import repository.domain.ClientRepository;
import repository.domain.MachineRepository;
import repository.domain.RentRepository;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;
import repository.redis.ClientRedisRepository;
import repository.redis.MachineRedisRepository;
import repository.redis.RentRedisRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class mainTest {
    private ClientManager clientManager;
    private MachineManager machineManager;
    private RentManager rentManager;
    private ClientMongoRepository clientMongoRepository;
    private MachineMongoRepository machineMongoRepository;
    private RentMongoRepository rentMongoRepository;
    private ClientRedisRepository clientRedisRepository;
    private MachineRedisRepository machineRedisRepository;
    private RentRedisRepository rentRedisRepository;
    private RentRepository rentRepository;
    private ClientRepository clientRepository;
    private MachineRepository machineRepository;

    @BeforeEach
    void beforeEach() {
        this.clientManager = new ClientManager();
        this.machineManager = new MachineManager();
        this.rentManager = new RentManager(clientManager, machineManager);
        this.clientMongoRepository = new ClientMongoRepository();
        this.machineMongoRepository = new MachineMongoRepository();
        this.rentMongoRepository = new RentMongoRepository();
        this.clientRedisRepository = new ClientRedisRepository();
        this.machineRedisRepository = new MachineRedisRepository();
        this.rentRedisRepository = new RentRedisRepository();
        this.clientRepository = new ClientRepository(clientMongoRepository, clientRedisRepository);
        this.machineRepository = new MachineRepository(machineMongoRepository, machineRedisRepository);
        this.rentRepository = new RentRepository(rentMongoRepository, rentRedisRepository);
        clientRedisRepository.clearCache();
        machineRedisRepository.clearCache();
        rentRedisRepository.clearCache();
    }
    @Test
    void test() throws Exception {
        // dodaj klienta
        ClientType intermediate = new Intermediate();
        Client client = new Client("AronStorm", intermediate);

        clientManager.addClient(client);

        assertEquals(clientRepository.getById(client.getEntityId()).getUsername(), "AronStorm");


        // dodaj maszyne
        Machine machine = new Machine(4, 8192, 400, Machine.SystemType.DEBIAN);
        machineManager.addMachine(machine);
        assertEquals(machineRepository.findAll().size(), 1);
        assertEquals(machineRepository.getById(machine.getEntityId()).getSystem().toString(), "DEBIAN");


        // przed wypożyczeniem
        assertEquals(client.getActiveRents(), 0);
        assertEquals(machine.isRented(), false);

        client.setType(intermediate);
        clientRepository.update(client);
        clientRepository.getById(client.getEntityId()).setType(intermediate);

        // dodaj wypożyczenie
        Rent rent = rentManager.addRent(client, machine);
        assertEquals(rentRepository.findAll().size(), 1);
        assertEquals(rentRepository.getById(rent.getEntityId()).getClient().getUsername(), "AronStorm");

        // zaktualizuj baze danych
        rentRepository.update(rent);

        // po wypożyczeniu
        assertEquals(client.getActiveRents(), 1);
        assertEquals(machine.isRented(), true);

        // dodaj drugie wypożyczenie dla tego samego klienta
        Machine machine1 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);
        machineManager.addMachine(machine1);
        assertEquals(machineRepository.findAll().size(), 2);

        Rent rent1 = rentManager.addRent(client, machine1);
        assertEquals(rentRedisRepository.size(), 2);


        // zaktualizuj baze danych
        rentRepository.update(rent1);

        // po wypożyczeniu drugiej maszyny
        assertEquals(client.getActiveRents(), 2);
        assertEquals(machine1.isRented(), true);

        // próba wypożyczenia za dużej ilości dostępnych maszyn
        Machine machine2 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        machineManager.addMachine(machine2);
        assertEquals(machineRedisRepository.size(), 3);


        assertThrows(Exception.class, ()->rentManager.addRent(client, machine2));

        // próba wypożyczenia maszyny, która jest już wypożyczona
        ClientType advanced = new Advanced();
        Client client1 = new Client("Dynamo", advanced);
        clientManager.addClient(client1);
        assertEquals(clientRedisRepository.getById(client1.getEntityId()).getType().getMaxRents(), 5);
        assertEquals(clientRedisRepository.size(), 2);


        assertThrows(Exception.class, ()->rentManager.addRent(client1, machine1));


        clientRepository.remove(client.getEntityId());
        machineRepository.remove(machine.getEntityId());
        rentRepository.remove(rent.getEntityId());
        machineRepository.remove(machine1.getEntityId());
        rentRepository.remove(rent.getEntityId());
        machineRepository.remove(machine2.getEntityId());
        clientRepository.remove(client1.getEntityId());
    }
}
