import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import managers.ClientManager;
import managers.RentManager;
import model.ClientMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;
import model.MachineMgd;
import model.RentMgd;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.mongo.AbstractMongoRepository;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;

public class RentManagerTest {
    private static RentManager rentManager;
    private static ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
    private static MachineMongoRepository machineMongoRepository = new MachineMongoRepository();

    private static RentMongoRepository rentMongoRepository = new RentMongoRepository();
    ClientTypeMgd intermediate = new IntermediateMgd();

    @BeforeAll
    public static void beforeAll() { rentManager = new RentManager();
        clientMongoRepository.clearDatabase();
        machineMongoRepository.clearDatabase();
        rentMongoRepository.clearDatabase();}

    @Test
    public void CreateTest() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", intermediate);
        MachineMgd machineMgd = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        assertEquals(clientMgd.getActiveRents(), 0);
        assertEquals(machineMgd.getIsRented(), false);
        RentMgd rentMgd = rentManager.addRent(clientMgd, machineMgd);
        assertEquals(rentManager.getRent(rentMgd.getEntityId()).getClient().getUsername(), clientMgd.getUsername());
        assertEquals(clientMgd.getActiveRents(), 1);
        assertEquals(machineMgd.getIsRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        MachineMgd machineMgd1 = new MachineMgd(3, 256, 300, MachineMgd.SystemType.WINDOWS7);
        MachineMgd machineMgd2 = new MachineMgd(3, 256, 500, MachineMgd.SystemType.FEDORA);
        RentMgd rentMgd1 = rentManager.addRent(clientMgd, machineMgd1);
        assertThrows(Exception.class, () ->{RentMgd rentMgd2 = rentManager.addRent(clientMgd, machineMgd2);});
        ClientMgd clientMgd1 = new ClientMgd("Storm", intermediate);
        assertThrows(Exception.class, () ->{RentMgd rentMgd2 = rentManager.addRent(clientMgd1, machineMgd1);});
        rentManager.removeRent(rentMgd.getEntityId());
        rentManager.removeRent(rentMgd1.getEntityId());
    }

    @Test
    public void ReadTest() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", intermediate);
        MachineMgd machineMgd = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        assertEquals(clientMgd.getActiveRents(), 0);
        assertEquals(machineMgd.getIsRented(), false);
        RentMgd rentMgd = rentManager.addRent(clientMgd, machineMgd);
        assertEquals(rentManager.getRent(rentMgd.getEntityId()).getClient().getUsername(), clientMgd.getUsername());
        assertEquals(clientMgd.getActiveRents(), 1);
        assertEquals(machineMgd.getIsRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        MachineMgd machineMgd1 = new MachineMgd(3, 256, 300, MachineMgd.SystemType.WINDOWS7);
        MachineMgd machineMgd2 = new MachineMgd(3, 256, 500, MachineMgd.SystemType.FEDORA);
        RentMgd rentMgd1 = rentManager.addRent(clientMgd, machineMgd1);
        assertEquals(rentManager.findAll().size(), 2);
        assertEquals(rentManager.getRent(rentMgd1.getEntityId()).getClient().getUsername(), clientMgd.getUsername());
        rentManager.removeRent(rentMgd.getEntityId());
        rentManager.removeRent(rentMgd1.getEntityId());
    }

    @Test
    public void DeleteTest() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", intermediate);
        MachineMgd machineMgd = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        assertEquals(clientMgd.getActiveRents(), 0);
        assertEquals(machineMgd.getIsRented(), false);
        RentMgd rentMgd = rentManager.addRent(clientMgd, machineMgd);
        assertEquals(rentManager.getRent(rentMgd.getEntityId()).getClient().getUsername(), clientMgd.getUsername());
        assertEquals(clientMgd.getActiveRents(), 1);
        assertEquals(machineMgd.getIsRented(), true);
        assertEquals(intermediate.getMaxRents(), 2);
        MachineMgd machineMgd1 = new MachineMgd(3, 256, 300, MachineMgd.SystemType.WINDOWS7);
        MachineMgd machineMgd2 = new MachineMgd(3, 256, 500, MachineMgd.SystemType.FEDORA);
        RentMgd rentMgd1 = rentManager.addRent(clientMgd, machineMgd1);
        assertEquals(rentManager.findAll().size(), 2);
        assertEquals(machineMgd.getIsRented(), true);
        assertEquals(machineMgd1.getIsRented(), true);
        rentManager.removeRent(rentMgd1.getEntityId());
        assertEquals(rentManager.findAll().size(), 1);
        assertEquals(machineMgd.getIsRented(), true);
//        assertEquals(machineMgd1.getIsRented(), false);
        rentManager.removeRent(rentMgd.getEntityId());
    }
}
