package managersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import managers.ClientManager;
import model.Advanced;
import model.Basic;
import model.Client;
import model.Intermediate;
import model.Machine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientManagerTest {
    private static EntityManager em;
    private static ClientManager cm;
    private static Client client1, client2, client3, client4;

    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        cm = new ClientManager(em);

        Basic basic = new Basic();
        Intermediate intermediate = new Intermediate();
        Advanced advanced = new Advanced();

        client1 = new Client("SzymonP", intermediate);
        client2 = new Client("MichalK", advanced);
        client3 = new Client("Podgor", basic);
        client4 = new Client("White115", intermediate);

        Machine machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10, false);
        Machine machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7, false);

        em.getTransaction().begin();
        em.persist(client1);
        em.persist(client2);
        em.persist(client3);
        em.persist(client4);
        em.persist(machine1);
        em.persist(machine2);
        em.getTransaction().commit();
    }
    @AfterAll
    static void AfterAll() {
        if(em != null) {
            em.close();
        }
    }

    @Test
    void findAll() {
        List<Client> list = cm.findAll();
        assertEquals(list.size(),4);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client3));
        assertTrue(list.contains(client4));
    }
    @Test
    void getClientTest() {
        Client client = cm.getClient(1);
        assertEquals(client.getID(), 1);
    }
    @Test
    void addClientTest() {
        Advanced advanced = new Advanced();
        Client client = cm.addClient(" AronStorm", advanced);
        List<Client> list = cm.findAll();
        assertTrue(list.contains(client));
    }
    @Test
    void removeClientTest() {
        assertEquals(cm.findAll().size(),5);
        cm.removeClient(client4.getID());
        assertEquals(cm.findAll().size(),4);
    }
}
