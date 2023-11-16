import static org.junit.jupiter.api.Assertions.assertEquals;

import model.AdvancedMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;
import model.MachineMgd;
import model.RentMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;

public class RentMongoRepositoryTest {
    private static RentMongoRepository rentMongoRepository;
    ClientTypeMgd intermediate = new IntermediateMgd();

    @BeforeAll
    public static void beforeAll() { rentMongoRepository = new RentMongoRepository(); }

    @Test
    public void CreateTest() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", intermediate);
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
        RentMgd rentMgd = new RentMgd(clientMgd, machineMgd);
        rentMongoRepository.add(rentMgd);
        assertEquals(rentMongoRepository.findByClient(clientMgd), rentMgd);
        rentMongoRepository.remove(rentMgd.getEntityId());
    }

    @Test
    public void ReadTest() throws Exception {
        rentMongoRepository.clearDatabase();
        ClientMgd clientMgd = new ClientMgd("Aron12", intermediate);
        ClientMgd clientMgd1 = new ClientMgd("Storm", intermediate);
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
        MachineMgd machineMgd1 = new MachineMgd(5, 512, 550, MachineMgd.SystemType.FEDORA);
        RentMgd rentMgd = new RentMgd(clientMgd, machineMgd);
        RentMgd rentMgd1 = new RentMgd(clientMgd1, machineMgd1);
        rentMongoRepository.add(rentMgd);
        rentMongoRepository.add(rentMgd1);
        assertEquals(rentMongoRepository.findAll().size(), 2);
        assertEquals(rentMongoRepository.findByMachine(machineMgd1), rentMgd1);
        rentMongoRepository.remove(rentMgd.getEntityId());
        rentMongoRepository.remove(rentMgd1.getEntityId());
    }

    @Test
    public void UpdateTest() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", intermediate);
        ClientMgd clientMgd1 = new ClientMgd("Dynamo", intermediate);
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
        RentMgd rentMgd = new RentMgd(clientMgd, machineMgd);
        rentMongoRepository.add(rentMgd);
        rentMgd.setClient(clientMgd1);
        assertEquals(rentMongoRepository.findByClient(clientMgd).getClient(), clientMgd);
        rentMongoRepository.update(rentMgd);
        assertEquals(rentMongoRepository.findByClient(clientMgd1).getClient(), clientMgd1);
        rentMongoRepository.remove(rentMgd.getEntityId());
    }
}
