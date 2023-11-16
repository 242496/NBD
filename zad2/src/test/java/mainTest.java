import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import managers.ClientManager;
import managers.MachineManager;
import managers.RentManager;
import model.AdvancedMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;
import model.MachineMgd;
import model.RentMgd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class mainTest {
    private ClientManager clientManager;
    private MachineManager machineManager;
    private RentManager rentManager;
    private ClientMongoRepository clientMongoRepository;
    private MachineMongoRepository machineMongoRepository;
    private RentMongoRepository rentMongoRepository;

    @BeforeEach
    void beforeEach() {
        this.clientManager = new ClientManager();
        this.machineManager = new MachineManager();
        this.rentManager = new RentManager();
        this.clientMongoRepository = new ClientMongoRepository();
        this.machineMongoRepository = new MachineMongoRepository();
        this.rentMongoRepository = new RentMongoRepository();
        clientMongoRepository.clearDatabase();
        machineMongoRepository.clearDatabase();
        rentMongoRepository.clearDatabase();
    }
    @Test
    void test() throws Exception {
        // dodaj klienta
        ClientTypeMgd intermediate = new IntermediateMgd();
        ClientMgd client = new ClientMgd("AronStorm", intermediate);
        clientManager.addClient(client);
        assertEquals(clientMongoRepository.findByUsername("AronStorm"), client);


        // dodaj maszyne
        MachineMgd machine = new MachineMgd(4, 8192, 400, MachineMgd.SystemType.DEBIAN);
        machineManager.addMachine(machine);
        assertEquals(machineMongoRepository.findAll().size(), 1);


        // przed wypożyczeniem
        assertEquals(client.getActiveRents(), 0);
        assertEquals(machine.getIsRented(), false);

        // dodaj wypożyczenie
        RentMgd rent = rentManager.addRent(client, machine);
        assertEquals(rentMongoRepository.findAll().size(), 1);
        assertEquals(rentMongoRepository.findByClient(client), rent);
        assertEquals(rentMongoRepository.findByMachine(machine), rent);


        // zaktualizuj baze danych
        rentMongoRepository.update(rent);

        // po wypożyczeniu
        assertEquals(client.getActiveRents(), 1);
        assertEquals(machine.getIsRented(), true);

        // dodaj drugie wypożyczenie dla tego samego klienta
        MachineMgd machine1 = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
        machineManager.addMachine(machine1);
        assertEquals(machineMongoRepository.findAll().size(), 2);

        RentMgd rentMgd = rentManager.addRent(client, machine1);
        assertEquals(rentMongoRepository.findAll().size(), 2);


        // zaktualizuj baze danych
        rentMongoRepository.update(rent);

        // po wypożyczeniu drugiej maszyny
        assertEquals(client.getActiveRents(), 2);
        assertEquals(machine1.getIsRented(), true);

        // próba wypożyczenia za dużej ilości dostępnych maszyn
        MachineMgd machine2 = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        machineManager.addMachine(machine2);
        assertEquals(machineMongoRepository.findAll().size(), 3);


        assertThrows(Exception.class, ()->rentManager.addRent(client, machine2));

        // próba wypożyczenia maszyny, która jest już wypożyczona
        ClientTypeMgd advanced = new AdvancedMgd();
        ClientMgd client1 = new ClientMgd("Dynamo", advanced);
        clientManager.addClient(client1);
        assertEquals(clientMongoRepository.findByUsername("Dynamo"), client1);
        assertEquals(clientMongoRepository.findAll().size(), 2);


        assertThrows(Exception.class, ()->rentManager.addRent(client1, machine1));


        System.out.println(rentMongoRepository.findAll().toString());
        clientMongoRepository.remove(client.getEntityId());
        machineMongoRepository.remove(machine.getEntityId());
        rentMongoRepository.remove(rent.getEntityId());
        machineMongoRepository.remove(machine1.getEntityId());
        rentMongoRepository.remove(rentMgd.getEntityId());
        machineMongoRepository.remove(machine2.getEntityId());
        clientMongoRepository.remove(client1.getEntityId());
    }


}
