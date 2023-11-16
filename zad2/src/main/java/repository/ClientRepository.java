package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.ClientMgd;
import model.UniqueId;
import repository.mongo.ClientMongoRepository;

public class ClientRepository implements Repository<ClientMgd> {
    private final ClientMongoRepository clientMongoRepository;

    public ClientRepository(ClientMongoRepository clientMongoRepository) {
        this.clientMongoRepository = clientMongoRepository;
    }

    @Override
    public ClientMgd add(ClientMgd entity) {
        clientMongoRepository.add(entity);
        return entity;
    }

    @Override
    public ClientMgd getById(UniqueId id) {
        return clientMongoRepository.findById(id);
    }


    public ClientMgd getByUsername(String Username) {
        return clientMongoRepository.findByUsername(Username);
    }

    @Override
    public void remove(UniqueId id) {
        clientMongoRepository.remove(id);
    }

    @Override
    public void update(ClientMgd entity) {
        clientMongoRepository.update(entity);
    }


    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<ClientMgd> findAll() {
        List<ClientMgd> clients = new ArrayList<>();
        List<ClientMgd> found = clientMongoRepository.findAll();
        clients.addAll(found);
        return clients;
    }
}
