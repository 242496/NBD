package repository.domain;

import domain.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mapper.ClientMapper;
import mongo_model.ClientMgd;
import redis_model.ClientRds;
import repository.Repository;
import repository.mongo.ClientMongoRepository;
import repository.redis.ClientRedisRepository;

public class ClientRepository implements Repository<Client> {
    private final ClientMongoRepository clientMongoRepository;
    private final ClientRedisRepository clientRedisRepository;

    public ClientRepository(ClientMongoRepository clientMongoRepository, ClientRedisRepository clientRedisRepository) {
        this.clientMongoRepository = clientMongoRepository;
        this.clientRedisRepository = clientRedisRepository;
    }

    @Override
    public Client add(Client entity) {
        clientRedisRepository.add(ClientMapper.toRedisDocument(entity));
        return ClientMapper.toDomainModel(clientMongoRepository.add(ClientMapper.toMongoDocument(entity)));
    }

    @Override
    public Client getById(UUID id){
        if (clientRedisRepository.checkConnection()){
            return ClientMapper.toDomainModel(clientRedisRepository.getById(id));
        } else {
            return ClientMapper.toDomainModel(clientMongoRepository.getById(id));
        }
    }

    @Override
    public void remove(UUID id){
        clientRedisRepository.remove(id);
        clientMongoRepository.remove(id);
    }

    @Override
    public Client update(Client client){
        clientRedisRepository.update(ClientMapper.toRedisDocument(client));
        return ClientMapper.toDomainModel(clientMongoRepository.update(ClientMapper.toMongoDocument(client)));
    }

    @Override
    public List<Client> findAll(){
        List<Client> clients = new ArrayList<>();
        if (clientRedisRepository.checkConnection()) {
            System.out.println(clientRedisRepository.checkConnection());
            List<ClientRds> found = clientRedisRepository.findAll();
            for (ClientRds client: found) {
                clients.add(ClientMapper.toDomainModel(client));
            }
        } else {
            List<ClientMgd> found = clientMongoRepository.findAll();
            for (ClientMgd client: found) {
                clients.add(ClientMapper.toDomainModel(client));
            }
        }
        return clients;
    }

    @Override
    public long size(){
        return findAll().size();
    }

    @Override
    public void close() throws Exception {
        clientMongoRepository.close();
        clientRedisRepository.close();
    }
}
