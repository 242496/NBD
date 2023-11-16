import static org.junit.jupiter.api.Assertions.assertEquals;

import managers.MachineManager;
import model.MachineMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.mongo.MachineMongoRepository;

public class MachineMongoRepositoryTest {

    private static MachineMongoRepository machineMongoRepository;
    private static MachineManager machineManager;

    @BeforeAll
    public static void beforeAll() { machineMongoRepository = new MachineMongoRepository();
    machineManager = new MachineManager();}

    @Test
    public void CreateTest() {
        machineMongoRepository.clearDatabase();
        MachineMgd machine1 = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
        machineManager.addMachine(machine1);
        assertEquals(machineMongoRepository.findById(machine1.getEntityId()), machine1);
        machineMongoRepository.remove(machine1.getEntityId());
    }

    @Test
    public void ReadTest() {
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.FEDORA);
        MachineMgd machineMgd1 = new MachineMgd(3, 512, 250, MachineMgd.SystemType.DEBIAN);
        machineMongoRepository.add(machineMgd1);
        machineMongoRepository.add(machineMgd);
        assertEquals(machineMongoRepository.findAll().size(), 2);
        assertEquals(machineMongoRepository.findById(machineMgd1.getEntityId()), machineMgd1);
        machineMongoRepository.remove(machineMgd.getEntityId());
        machineMongoRepository.remove(machineMgd1.getEntityId());
    }

    @Test
    public void UpdateTest() {
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS10);
        machineMongoRepository.add(machineMgd);
        machineMgd.setSystem(MachineMgd.SystemType.WINDOWS7);
        assertEquals(machineMongoRepository.findById(machineMgd.getEntityId()).getSystem(), MachineMgd.SystemType.WINDOWS10);
        machineMongoRepository.update(machineMgd);
        assertEquals(machineMongoRepository.findById(machineMgd.getEntityId()).getSystem(), MachineMgd.SystemType.WINDOWS7);
        machineMongoRepository.remove(machineMgd.getEntityId());
    }

    @Test
    public void DeleteTest() {
        machineMongoRepository.clearDatabase();
        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS10);
        machineMongoRepository.add(machineMgd);
        assertEquals(machineMongoRepository.findAll().size(), 1);
        machineMongoRepository.remove(machineMgd.getEntityId());
        assertEquals(machineMongoRepository.findAll().size(), 0);
    }
}
