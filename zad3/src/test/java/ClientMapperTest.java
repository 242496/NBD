import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Client;
import mapper.ClientMapper;
import mongo_model.AdvancedMgd;
import mongo_model.ClientMgd;
import mongo_model.ClientTypeMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientMapperTest {
    private static ClientMapper clientMapper;
    ClientTypeMgd advanced = new AdvancedMgd();

    @BeforeAll
    public static void beforeAll(){
        clientMapper = new ClientMapper();
    }

    @Test
    public void toDomainModel() {
        ClientMgd clientMgd = new ClientMgd("Aron", advanced, 0);
        Client client = clientMapper.toDomainModel(clientMgd);
        assertEquals(Client.class, client.getClass());
        assertEquals(clientMgd.getUsername(), client.getUsername());
        assertEquals(clientMgd.getActiveRents(), client.getActiveRents());
    }

    @Test
    public void toMongoDocument() {
        Client client = new Client("Aron", advanced);
        ClientMgd clientMgd = clientMapper.toMongoDocument(client);
        assertEquals(Client.class, client.getClass());
        assertEquals(clientMgd.getUsername(), client.getUsername());
        assertEquals(clientMgd.getActiveRents(), client.getActiveRents());
    }
}
