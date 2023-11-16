import static org.junit.jupiter.api.Assertions.assertEquals;

import managers.ClientManager;
import model.AdvancedMgd;
import model.BasicMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
import model.MachineMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientManagerTest {
    private static ClientManager clientManager;
    ClientTypeMgd basic = new BasicMgd();

    @BeforeAll
    public static void beforeAll(){
        clientManager = new ClientManager();
    }

    @Test
    public void CreateTest() {
        ClientMgd clientMgd = new ClientMgd("Aron", basic);
        clientManager.addClient(clientMgd);
        assertEquals(clientMgd.getUsername(), "Aron");
        assertEquals(clientMgd.getType(), basic);
        assertEquals(clientMgd.getActiveRents(), 0);
        clientManager.removeClient(clientMgd.getEntityId());
    }

    @Test
    public void ReadTest() {
        ClientMgd clientMgd = new ClientMgd("Aron12", basic);
        ClientMgd clientMgd1 = new ClientMgd("Dynamo", basic);
        clientManager.addClient(clientMgd);
        clientManager.addClient(clientMgd1);
        assertEquals(clientManager.getClient("Dynamo").getUsername(), "Dynamo");
        clientManager.removeClient(clientMgd.getEntityId());
        clientManager.removeClient(clientMgd1.getEntityId());
    }

    @Test
    public void DeleteTest() {
        ClientMgd clientMgd = new ClientMgd("Aron2", basic);
        clientManager.addClient(clientMgd);
        assertEquals(clientManager.getClient("Aron2").getUsername(), "Aron2");
        assertEquals(clientManager.findAllClients().size(), 1);
        clientManager.removeClient(clientMgd.getEntityId());
        assertEquals(clientManager.findAllClients().size(), 0);
    }
}
