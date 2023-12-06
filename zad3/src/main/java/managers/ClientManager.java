package managers;

import domain.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mapper.ClientTypeMapper;
import repository.domain.ClientRepository;
import repository.mongo.ClientMongoRepository;
import repository.redis.ClientRedisRepository;

public class ClientManager {
    private final ClientRepository clientRepository;

    public ClientManager() {
        this.clientRepository = new ClientRepository(new ClientMongoRepository(), new ClientRedisRepository());
    }

    public Client getClient(UUID id) {
        return clientRepository.getById(id);
    }

    public List<Client> findAllClients() {
        List<Client> list = new ArrayList<>();
        clientRepository.findAll().stream().forEach(client -> list.add(client));
        return list;
    }

    public Client addClient(Client client) {
        client.setTypeRds(ClientTypeMapper.toRedisDocument(client.getType()));
        client.setTypeMgd(ClientTypeMapper.toMongoDocument(client.getType()));
        client.setType(ClientTypeMapper.toDomainModel(client.getTypeRds()));
        clientRepository.add(client);
        client.setActiveRents(0);
        return client;
    }

    public void removeClient(UUID id) {
        clientRepository.remove(id);
        clientRepository.findAll().clear();
    }
}
