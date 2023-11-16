import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import domain.Advanced;
import domain.Client;
import domain.ClientType;
import mapper.ClientMapper;
import model.AdvancedMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
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
        ClientMgd clientMgd = new ClientMgd("Aron", advanced);
        Client client = clientMapper.toDomainModel(clientMgd);
        assertEquals(Client.class, client.getClass());
        assertEquals(clientMgd.getUsername(), client.getUsername());
        assertEquals(clientMgd.getActiveRents(), client.getActiveRents());
        assertEquals(clientMgd.getType(), client.getType());
    }

    @Test
    public void toMongoDocument() {
        Client client = new Client("Aron", advanced);
        ClientMgd clientMgd = clientMapper.toMongoDocument(client);
        assertEquals(Client.class, client.getClass());
        assertEquals(clientMgd.getUsername(), client.getUsername());
        assertEquals(clientMgd.getActiveRents(), client.getActiveRents());
        assertEquals(clientMgd.getType(), client.getType());
    }
}
