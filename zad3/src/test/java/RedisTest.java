import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.Advanced;
import domain.Basic;
import domain.Client;
import domain.ClientType;
import domain.Machine;
import managers.ClientManager;
import managers.MachineManager;
import managers.RentManager;
import mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.exceptions.JedisException;
import repository.mongo.ClientMongoRepository;
import repository.mongo.MachineMongoRepository;
import repository.mongo.RentMongoRepository;
import repository.redis.ClientRedisRepository;
import repository.redis.MachineRedisRepository;
import repository.redis.RentRedisRepository;

public class RedisTest {
    ClientRedisRepository clientRedisRepository;
    ClientMongoRepository clientMongoRepository;
    MachineRedisRepository machineRedisRepository;
    MachineMongoRepository machineMongoRepository;
    RentRedisRepository rentRedisRepository;
    ClientManager clientManager;
    MachineManager machineManager;
    RentManager rentManager;

    @BeforeEach
    public void beforeEach() throws Exception {
        clientRedisRepository = new ClientRedisRepository();
        clientMongoRepository = new ClientMongoRepository();
        machineRedisRepository = new MachineRedisRepository();
        machineMongoRepository = new MachineMongoRepository();
        rentRedisRepository = new RentRedisRepository();
        machineMongoRepository = new MachineMongoRepository();
        clientManager = new ClientManager();
        machineManager = new MachineManager();
        rentManager = new RentManager(clientManager, machineManager);
        clientRedisRepository.clearCache();
        rentRedisRepository.clearCache();
        machineRedisRepository.clearCache();
    }

    @Test
    public void addToMongoIfNoRedisTest() throws Exception {
        ClientType advanced = new Advanced();
        Client client = new Client("Aron", advanced);

        clientManager.addClient(client);

        assertEquals(clientManager.findAllClients().size(), 1);
        assertEquals(clientRedisRepository.getById(client.getEntityId()).getUsername(), "Aron");
        assertEquals(clientRedisRepository.getById(client.getEntityId()).getType().getMaxDays(), 90);
        //Try to set checkconnection to false so data will be taken from mongo
        clientRedisRepository.close();
        assertThrows(JedisException.class, ()->clientRedisRepository.getById(client.getEntityId()).getUsername());
        assertEquals(clientMongoRepository.getById(client.getEntityId()).getUsername(), "Aron");
        assertEquals(clientMongoRepository.getById(client.getEntityId()).getType().getMaxDays(), 90);

        clientMongoRepository.remove(client.getEntityId());
        clientMongoRepository.update(ClientMapper.toMongoDocument(client));
    }

    @Test
    public void clearCache() throws Exception {
        ClientType advanced = new Advanced();
        ClientType basic = new Basic();
        Client client = new Client("Aron", advanced);
        Client client1 = new Client("Storm", basic);

        clientManager.addClient(client);
        clientManager.addClient(client1);

        assertEquals(clientManager.findAllClients().size(), 2);

        Machine machine = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);
        Machine machine1 = new Machine(4, 1024, 500, Machine.SystemType.WINDOWS10);
        Machine machine2 = new Machine(2, 1024, 200, Machine.SystemType.DEBIAN);

        machineManager.addMachine(machine);
        machineManager.addMachine(machine1);
        machineManager.addMachine(machine2);

        assertEquals(machineManager.findAllMachines().size(), 3);

        rentManager.addRent(client1, machine2);


        assertEquals(clientRedisRepository.findAll().size(), 2);
        assertEquals(machineRedisRepository.findAll().size(), 3);
        assertEquals(rentRedisRepository.findAll().size(), 1);

        clientRedisRepository.clearCache();

        assertEquals(clientRedisRepository.findAll().size(), 0);
        assertEquals(machineRedisRepository.findAll().size(), 0);
        assertEquals(rentRedisRepository.findAll().size(), 0);

        assertEquals(clientManager.findAllClients().size(), 0);
        assertEquals(machineManager.findAllMachines().size(), 0);
        assertEquals(rentManager.findAll().size(), 0);
    }

    @Test
    public void clearThisCache() throws Exception {
        ClientType advanced = new Advanced();
        ClientType basic = new Basic();
        Client client = new Client("Aron", advanced);
        Client client1 = new Client("Storm", basic);

        clientManager.addClient(client);
        clientManager.addClient(client1);

        assertEquals(clientManager.findAllClients().size(), 2);

        Machine machine = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);
        Machine machine1 = new Machine(4, 1024, 500, Machine.SystemType.WINDOWS10);
        Machine machine2 = new Machine(2, 1024, 200, Machine.SystemType.DEBIAN);

        machineManager.addMachine(machine);
        machineManager.addMachine(machine1);
        machineManager.addMachine(machine2);

        assertEquals(machineManager.findAllMachines().size(), 3);

        rentManager.addRent(client1, machine2);


        assertEquals(clientRedisRepository.findAll().size(), 2);
        assertEquals(machineRedisRepository.findAll().size(), 3);
        assertEquals(rentRedisRepository.findAll().size(), 1);

        clientRedisRepository.clearThis();

        assertEquals(clientRedisRepository.findAll().size(), 0);
        assertEquals(machineRedisRepository.findAll().size(), 3);
        assertEquals(rentRedisRepository.findAll().size(), 1);

        machineRedisRepository.clearThis();

        assertEquals(clientRedisRepository.findAll().size(), 0);
        assertEquals(machineRedisRepository.findAll().size(), 0);
        assertEquals(rentRedisRepository.findAll().size(), 1);

        rentRedisRepository.clearThis();

        assertEquals(clientRedisRepository.findAll().size(), 0);
        assertEquals(machineRedisRepository.findAll().size(), 0);
        assertEquals(rentRedisRepository.findAll().size(), 0);

        assertEquals(clientManager.findAllClients().size(), 0);
        assertEquals(machineManager.findAllMachines().size(), 0);
        assertEquals(rentManager.findAll().size(), 0);
    }
}
