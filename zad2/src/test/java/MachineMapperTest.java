import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Client;
import domain.Machine;
import mapper.ClientMapper;
import mapper.MachineMapper;
import model.ClientMgd;
import model.MachineMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MachineMapperTest {

    private static MachineMapper machineMapper;

    @BeforeAll
    public static void beforeAll(){
        machineMapper = new MachineMapper();
    }

    @Test
    public void toDomainModel() {
        MachineMgd machineMgd = new MachineMgd(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        Machine machine = machineMapper.toDomainModel(machineMgd);
        assertEquals(Machine.class, machine.getClass());
        assertEquals(machineMgd.getCPUs(), machine.getCPUs());
        assertEquals(machineMgd.getRAM(), machine.getRAM());
        assertEquals(machineMgd.getDisk(), machine.getDisk());
        assertEquals(machineMgd.getSystem(), machine.getSystem());
        assertEquals(machineMgd.getIsRented(), machine.isRented());
    }

    @Test
    public void toMongoDocument() {
        Machine machine = new Machine(2, 256, 200, MachineMgd.SystemType.WINDOWS10);
        MachineMgd machineMgd = machineMapper.toMongoDocument(machine);
        assertEquals(Machine.class, machine.getClass());
        assertEquals(machineMgd.getCPUs(), machine.getCPUs());
        assertEquals(machineMgd.getRAM(), machine.getRAM());
        assertEquals(machineMgd.getDisk(), machine.getDisk());
        assertEquals(machineMgd.getSystem(), machine.getSystem());
        assertEquals(machineMgd.getIsRented(), machine.isRented());
    }
}
