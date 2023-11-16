import static org.junit.jupiter.api.Assertions.assertEquals;

import managers.MachineManager;
import model.MachineMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MachineManagerTest {
    private static MachineManager machineManager;

    @BeforeAll
    public static void beforeAll() { machineManager = new MachineManager(); }

//    @Test
//    public void CreateTest() {
//        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.DEBIAN);
//        machineManager.addMachine(machineMgd);
//        assertEquals(machineMgd.getCPUs(), 3);
//        assertEquals(machineMgd.getRAM(), 512);
//        assertEquals(machineMgd.getDisk(), 250);
//        assertEquals(machineMgd.getSystem(), MachineMgd.SystemType.DEBIAN);
//        assertEquals(machineMgd.getIsRented(), false);
//    }
//
//    @Test
//    public void ReadTest() {
//        MachineMgd machineMgd = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
//        MachineMgd machineMgd1 = new MachineMgd(3, 512, 250, MachineMgd.SystemType.FEDORA);
//        machineManager.addMachine(machineMgd);
//        machineManager.addMachine(machineMgd1);
//        assertEquals(machineManager.findAllMachines().size(), 2);
//        machineManager.removeMachine(machineMgd.getEntityId());
//        machineManager.removeMachine(machineMgd1.getEntityId());
//    }
//
//    @Test
//    public void DeleteTest() {
//        MachineMgd machineMgd = new MachineMgd(3, 512, 250, MachineMgd.SystemType.WINDOWS7);
//        machineManager.addMachine(machineMgd);
//        assertEquals(machineManager.findAllMachines().size(), 1);
//        machineManager.removeMachine(machineMgd.getEntityId());
//        assertEquals(machineManager.findAllMachines().size(), 0);
//    }
}
