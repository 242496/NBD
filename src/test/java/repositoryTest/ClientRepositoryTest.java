package repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import model.Advanced;
import model.Basic;
import model.Client;
import model.Intermediate;
import model.Machine;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;

public class ClientRepositoryTest {
    private static EntityManager em;
    private static ClientRepository cr;
    private static Client client1, client2, client3, client4;

    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        cr = new ClientRepository(em);

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
    void addTest() {
        cr.add(client1);
        assertTrue(em.contains(client1));
        cr.add(client2);
        assertTrue(em.contains(client2));
    }

    @Test
    void removeTest() {
        cr.add(client1);
        assertTrue(em.contains(client1));
        cr.remove(client1);
        assertFalse(em.contains(client1));
    }

    @Test
    void getByIDTest() {
        client1.setID(100);
        cr.add(client1);
        assertTrue(em.contains(client1));
        assertEquals(client1.getID(), 100);
    }

    @Test
    void findAllTest() {
        assertTrue(em.contains(client1));
        assertTrue(em.contains(client2));
        assertTrue(em.contains(client3));
        assertTrue(em.contains(client4));
        List<Client> list = cr.findAll();
        assertEquals(4,list.size());
    }
}
