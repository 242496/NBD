import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Basic;
import domain.Client;
import domain.ClientType;
import managers.ClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.domain.ClientRepository;
import repository.mongo.ClientMongoRepository;
import repository.redis.ClientRedisRepository;

public class ClientManagerTest {
    private static ClientManager clientManager;
    private static ClientMongoRepository clientMongoRepository = new ClientMongoRepository();
    private static ClientRedisRepository clientRedisRepository = new ClientRedisRepository();
    private static ClientRepository clientRepository = new ClientRepository(clientMongoRepository, clientRedisRepository);
    ClientType basic = new Basic();

    @BeforeAll
    public static void beforeAll(){
        clientManager = new ClientManager();
        clientRedisRepository.clearCache();
    }

    @Test
    public void CreateTest() {
        Client client = new Client("Aron", basic);
        clientManager.addClient(client);
        assertEquals(client.getUsername(), "Aron");
        assertEquals(client.getType().getMaxRents(), 1);
        assertEquals(client.getActiveRents(), 0);
        clientManager.removeClient(client.getEntityId());
    }

    @Test
    public void ReadTest() {
        Client client = new Client("Aron12", basic);
        Client client1 = new Client("Dynamo", basic);
        clientManager.addClient(client);
        clientManager.addClient(client1);
        assertEquals(clientManager.getClient(client1.getEntityId()).getUsername(), "Dynamo");
        clientManager.removeClient(client.getEntityId());
        clientManager.removeClient(client1.getEntityId());
    }
}
