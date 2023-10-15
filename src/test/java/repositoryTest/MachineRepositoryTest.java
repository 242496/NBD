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
import repository.MachineRepository;

public class MachineRepositoryTest {
    private static EntityManager em;
    private static MachineRepository mr;
    private static Machine machine1, machine2;
    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        mr = new MachineRepository(em);

        Basic basic = new Basic();
        Intermediate intermediate = new Intermediate();
        Advanced advanced = new Advanced();

        Client client1 = new Client("SzymonP", intermediate);
        Client client2 = new Client("MichalK", advanced);
        Client client3 = new Client("Podgor", basic);
        Client client4 = new Client("White115", intermediate);

        machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10, false);
        machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7, false);

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
        mr.add(machine1);
        assertTrue(em.contains(machine1));
        mr.add(machine2);
        assertTrue(em.contains(machine2));
    }

    @Test
    void removeTest() {
        mr.add(machine1);
        assertTrue(em.contains(machine1));
        mr.remove(machine1);
        assertFalse(em.contains(machine1));
    }

    @Test
    void getByIDTest() {
        machine1.setID(100);
        mr.add(machine1);
        assertTrue(em.contains(machine1));
        assertEquals(machine1.getID(), 100);
    }

    @Test
    void findAllTest() {
        assertTrue(em.contains(machine1));
        assertTrue(em.contains(machine2));
        List<Machine> list = mr.findAll();
        assertEquals(2,list.size());
    }
}
