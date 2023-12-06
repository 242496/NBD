import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Machine;
import managers.MachineManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.redis.ClientRedisRepository;

public class MachineManagerTest {
    private static MachineManager machineManager;
    private static ClientRedisRepository clientRedisRepository;

    @BeforeAll
    public static void beforeAll() { machineManager = new MachineManager();
        clientRedisRepository = new ClientRedisRepository();
        clientRedisRepository.clearCache();
    }

    @Test
    public void CreateTest() {
        Machine machine = new Machine(3, 512, 250, Machine.SystemType.DEBIAN);
        machineManager.addMachine(machine);
        assertEquals(machine.getCPUs(), 3);
        assertEquals(machine.getRAM(), 512);
        assertEquals(machine.getDisk(), 250);
        assertEquals(machine.getSystem().toString(), "DEBIAN");
        assertEquals(machine.isRented(), false);
        machineManager.removeMachine(machine.getEntityId());
    }

    @Test
    public void ReadTest() {
        Machine machine = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        Machine machine1 = new Machine(3, 512, 250, Machine.SystemType.FEDORA);
        machineManager.addMachine(machine);
        machineManager.addMachine(machine1);
        assertEquals(machineManager.findAllMachines().size(), 2);
        machineManager.removeMachine(machine.getEntityId());
        machineManager.removeMachine(machine1.getEntityId());
    }

    @Test
    public void DeleteTest() {
        Machine machine = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);
        machineManager.addMachine(machine);
        assertEquals(machineManager.findAllMachines().size(), 1);
        machineManager.removeMachine(machine.getEntityId());
        assertEquals(machineManager.findAllMachines().size(), 0);
    }
}
