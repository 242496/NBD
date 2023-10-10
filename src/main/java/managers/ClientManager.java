package managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import model.Client;
import model.ClientType;
import repository.ClientRepository;

public class ClientManager {
    private ClientRepository clientRepository;
    private EntityManager em;
    private EntityTransaction et;

    public ClientManager(EntityManager entityManager) {
        em = entityManager;
        clientRepository = new ClientRepository(entityManager);
    }

    public void removeClient(long ID) {
        et = em.getTransaction();
        et.begin();
        clientRepository.remove(clientRepository.getByID(ID));
        et.commit();
    }

    public List<Client> findAll() {
        et = em.getTransaction();
        et.begin();
        List<Client> list = clientRepository.findAll();
        et.commit();
        return list;
    }

    public Client getClient(long ID) {
        et = em.getTransaction();
        et.begin();
        Client client = clientRepository.getByID(ID);
        et.commit();
        return client;
    }

    public Client addClient(String Username, ClientType Type) {
        //TODO cannot add client with the same username
        Client client = new Client(Username, Type);
        et = em.getTransaction();
        et.begin();
        clientRepository.add(client);
        et.commit();
        return client;
    }
}
