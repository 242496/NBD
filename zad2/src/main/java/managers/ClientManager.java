package managers;

import domain.Client;
import java.util.ArrayList;
import java.util.List;
import mapper.ClientMapper;
import model.ClientMgd;
import model.RentMgd;
import model.UniqueId;
import repository.ClientRepository;
import repository.mongo.ClientMongoRepository;

public class ClientManager {
    private final ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientManager() {
        this.clientRepository = new ClientRepository(new ClientMongoRepository());
        this.clientMapper = new ClientMapper();
    }

    public Client getClient(String Username) {
        return clientMapper.toDomainModel(clientRepository.getByUsername(Username));
    }

    public List<Client> findAllClients() {
        List<ClientMgd> clientsList = clientRepository.findAll();
        List<Client> list = new ArrayList<Client>();
        for(ClientMgd c :clientsList){
            list.add(clientMapper.toDomainModel(c));
        }
        return list;
    }

    public ClientMgd addClient(ClientMgd client) {
        clientRepository.add(client);
        client.setActiveRents(0);
        return client;
    }

    public void removeClient(UniqueId ID) {
        clientRepository.remove(ID);
        clientRepository.findAll().clear();
    }
}
