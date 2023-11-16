package managersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
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
    private static EntityTransaction et;
    private static Client client1, client2, client3, client4;

    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        cm = new ClientManager(em);
        et = em.getTransaction();

        Basic basic = new Basic();
        Intermediate intermediate = new Intermediate();
        Advanced advanced = new Advanced();

        client1 = new Client("SzymonP", intermediate);
        client2 = new Client("MichalK", advanced);
        client3 = new Client("Podgor", basic);
        client4 = new Client("White115", intermediate);

        Machine machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10, false);
        Machine machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7, false);

        et.begin();
        em.persist(client1);
        em.persist(client2);
        em.persist(client3);
        em.persist(client4);
        em.persist(machine1);
        em.persist(machine2);
        et.commit();
    }
    @AfterAll
    static void AfterAll() {
        if(em != null) {
            em.close();
        }
    }

    @Test
    void findAll() {
        et.begin();
        List<Client> list = cm.findAll();
        et.commit();

        assertEquals(list.size(),4);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client3));
        assertTrue(list.contains(client4));
    }
    @Test
    void addClientTest() {
        Advanced advanced = new Advanced();

        et.begin();
        Client client = cm.addClient(" AronStorm", advanced);
        et.commit();

        et.begin();
        List<Client> list = cm.findAll();
        et.commit();

        assertTrue(list.contains(client));
    }
    @Test
    void removeClientTest() {
        et.begin();
        assertEquals(cm.findAll().size(),5);
        et.commit();

        et.begin();
        cm.removeClient(client4.getID());
        et.commit();

        et.begin();
        assertEquals(cm.findAll().size(),4);
        et.commit();
    }
}
