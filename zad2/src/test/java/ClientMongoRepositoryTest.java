import static org.junit.jupiter.api.Assertions.assertEquals;

import model.AdvancedMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.mongo.ClientMongoRepository;

public class ClientMongoRepositoryTest {
    private static ClientMongoRepository clientMongoRepository;
    ClientTypeMgd advanced = new AdvancedMgd();

    @BeforeAll
    public static void beforeAll(){
        clientMongoRepository = new ClientMongoRepository();
    }

    @Test
    public void CreateTest() {
        ClientMgd clientMgd = new ClientMgd("Aron", advanced);
        clientMongoRepository.add(clientMgd);
        assertEquals(clientMongoRepository.findByUsername("Aron"), clientMgd);
        clientMongoRepository.remove(clientMgd.getEntityId());
    }

    @Test
    public void ReadTest() {
        ClientMgd clientMgd = new ClientMgd("Aron1", advanced);
        ClientMgd clientMgd1 = new ClientMgd("Storm", advanced);
        clientMongoRepository.add(clientMgd);
        clientMongoRepository.add(clientMgd1);
        assertEquals(clientMongoRepository.findAll().size(), 2);
        assertEquals(clientMongoRepository.findByUsername("Storm"), clientMgd1);
        clientMongoRepository.remove(clientMgd.getEntityId());
        clientMongoRepository.remove(clientMgd1.getEntityId());
        clientMongoRepository.clearDatabase();
    }

    @Test
    public void UpdateTest() {
        ClientMgd clientMgd = new ClientMgd("Dynamo", advanced);
        clientMongoRepository.add(clientMgd);
        clientMgd.setUsername("DynamoXD");
        assertEquals(clientMongoRepository.findByUsername("Dynamo"), clientMgd);
        assertEquals(clientMongoRepository.findByUsername("DynamoXD"), null);
        clientMongoRepository.update(clientMgd);
        assertEquals(clientMongoRepository.findByUsername("Dynamo"), null);
        assertEquals(clientMongoRepository.findByUsername("DynamoXD"), clientMgd);
        clientMongoRepository.remove(clientMgd.getEntityId());
    }

    @Test
    public void DeleteTest() {
        ClientMgd clientMgd = new ClientMgd("Aron12", advanced);
        clientMongoRepository.add(clientMgd);
        assertEquals(clientMongoRepository.findAll().size(), 1);
        clientMongoRepository.remove(clientMgd.getEntityId());
        assertEquals(clientMongoRepository.findAll().size(), 0);
    }

}
