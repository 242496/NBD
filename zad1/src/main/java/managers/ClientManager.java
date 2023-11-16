package managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;
import model.Client;
import model.ClientType;
import repository.ClientRepository;

public class ClientManager {
    private final ClientRepository clientRepository;
    private final EntityManager em;
    private EntityTransaction et;

    public ClientManager(EntityManager entityManager) {
        em = entityManager;
        clientRepository = new ClientRepository(entityManager);
    }

    public void removeClient(UUID ID) {
        clientRepository.remove(clientRepository.getByID(ID));
    }

    public List<Client> findAll() {
        List<Client> list = clientRepository.findAll();
        return list;
    }

    public Client addClient(String Username, ClientType Type) {
        Client client = new Client(Username, Type);
        clientRepository.add(client);
        return client;
    }
}
