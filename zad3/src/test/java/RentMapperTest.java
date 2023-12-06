import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Client;
import domain.Machine;
import domain.Rent;
import mapper.RentMapper;
import mongo_model.AdvancedMgd;
import mongo_model.ClientMgd;
import mongo_model.ClientTypeMgd;
import mongo_model.MachineMgd;
import mongo_model.RentMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RentMapperTest {
    private static RentMapper rentMapper;
    ClientTypeMgd advanced = new AdvancedMgd();

    @BeforeAll
    public static void beforeAll(){
        rentMapper = new RentMapper();
    }

    @Test
    public void toDomainModel() throws Exception {
        ClientMgd clientMgd = new ClientMgd("Aron", advanced, 0);
        MachineMgd machineMgd = new MachineMgd(2, 256, 200, Machine.SystemType.WINDOWS10, false);
        RentMgd rentMgd = new RentMgd(clientMgd, machineMgd);
        Rent rent = rentMapper.toDomainModel(rentMgd);
        assertEquals(Rent.class, rent.getClass());
        assertEquals(rent.getBeginTime(), rentMgd.getBeginTime());
        assertEquals(rent.getEndTime(), rentMgd.getEndTime());
        assertEquals(rent.getClient().getUsername(), rentMgd.getClient().getUsername());
        assertEquals(rent.getClient().getActiveRents(), rentMgd.getClient().getActiveRents());
        assertEquals(rent.getClient().getTypeMgd(), rentMgd.getClient().getType());
        assertEquals(rent.getMachine().getCPUs(), rentMgd.getMachine().getCPUs());
        assertEquals(rent.getMachine().getRAM(), rentMgd.getMachine().getRAM());
        assertEquals(rent.getMachine().getDisk(), rentMgd.getMachine().getDisk());
        assertEquals(rent.getMachine().getBaseCost(), rentMgd.getMachine().getBaseCost());
        assertEquals(rent.getMachine().getSystem(), rentMgd.getMachine().getSystem());
        assertEquals(rent.getMachine().isRented(), rentMgd.getMachine().getIsRented());
    }

    @Test
    public void toMongoDocument() throws Exception {
        Client client = new Client("Aron", advanced);
        Machine machine = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        Rent rent = new Rent(client, machine);
        RentMgd rentMgd = rentMapper.toMongoDocument(rent);
        assertEquals(Rent.class, rent.getClass());
        assertEquals(rent.getBeginTime(), rentMgd.getBeginTime());
        assertEquals(rent.getEndTime(), rentMgd.getEndTime());
        assertEquals(rent.getClient().getUsername(), rentMgd.getClient().getUsername());
        assertEquals(rent.getClient().getActiveRents(), rentMgd.getClient().getActiveRents());
        assertEquals(rent.getClient().getTypeMgd(), rentMgd.getClient().getType());
        assertEquals(rent.getMachine().getCPUs(), rentMgd.getMachine().getCPUs());
        assertEquals(rent.getMachine().getRAM(), rentMgd.getMachine().getRAM());
        assertEquals(rent.getMachine().getDisk(), rentMgd.getMachine().getDisk());
        assertEquals(rent.getMachine().getBaseCost(), rentMgd.getMachine().getBaseCost());
        assertEquals(rent.getMachine().getSystem(), rentMgd.getMachine().getSystem());
        assertEquals(rent.getMachine().isRented(), rentMgd.getMachine().getIsRented());
    }
}
