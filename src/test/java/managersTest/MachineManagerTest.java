package managersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class MachineManagerTest {
    private static EntityManager em;
    private static MachineManager mm;
    private static Machine machine1, machine2;

    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        mm = new MachineManager(em);

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
    void findAllTest() {
        List<Machine> list = mm.findAll();
        assertTrue(list.contains(machine1));
    }
    @Test
    void getMachineTest() {
        Machine machine = mm.getMachine(1);
        assertEquals(machine.getID(), 1);
    }
    @Test
    void addClientTest() {
        Machine machine = mm.addMachine(4, 8192, 400, Machine.SystemType.DEBIAN, false);
        List<Machine> list = mm.findAll();
        assertTrue(list.contains(machine));
    }
    @Test
    void removeClientTest() {
        assertEquals(mm.findAll().size(),3);
        mm.removeMachine(machine2.getID());
        assertEquals(mm.findAll().size(),2);
    }
}
