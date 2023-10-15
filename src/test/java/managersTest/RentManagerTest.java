package managersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import model.Advanced;
import model.Basic;
import model.Client;
import model.Intermediate;
import model.Machine;
import model.Rent;
import org.apache.avalon.framework.parameters.ParameterException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RentManagerTest {
    private static EntityManager em;
    private static RentManager rm;
    private static Client client1, client2, client3, client4;
    private static Machine machine1, machine2, machine3;
    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        rm = new RentManager(em);

        Basic basic = new Basic();
        Intermediate intermediate = new Intermediate();
        Advanced advanced = new Advanced();

        client1 = new Client("SzymonP", intermediate);
        client2 = new Client("MichalK", advanced);
        client3 = new Client("Podgor", basic);
        client4 = new Client("White115", intermediate);

        machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10, false);
        machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7, false);
        machine3 = new Machine(4, 8192, 400, Machine.SystemType.DEBIAN, false);


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
    void addRentTest() throws ParameterException {
        Rent rent = rm.addRent(client1, machine3);
        List<Rent> list = rm.findAll();
        assertTrue(list.contains(rent));
        assertEquals(1, client1.getActiveRents());
        rm.removeRent(rent.getID());
    }

    @Test
    void rentsLimitTest() throws ParameterException {
        Basic basic = new Basic();
        int activeRents = client3.getActiveRents();
        assertEquals(1, basic.getMaxRents());
        assertEquals(0, activeRents);
        Rent rent = rm.addRent(client3, machine3);
        activeRents = client3.getActiveRents();
        assertEquals(1, activeRents);
        assertThrows(RejectedExecutionException.class, ()->rm.addRent(client3, machine2));
        rm.removeRent(rent.getID());
    }

    @Test
    void alreadyRentedMachineTest() throws ParameterException {
        assertFalse(machine1.isRented());
        Rent rent = rm.addRent(client4, machine1);
        assertTrue(machine1.isRented());
        assertThrows(RejectedExecutionException.class, ()->rm.addRent(client3, machine1));
    }

    @Test
    void removeRentTest() throws ParameterException {
        Rent rent = rm.addRent(client1, machine1);
        List<Rent> list = rm.findAll();
        assertTrue(list.contains(rent));
        assertEquals(1, client1.getActiveRents());
        assertEquals(rent.getID(), 1);
        rm.removeRent(rent.getID());
        list = rm.findAll();
        assertFalse(list.contains(rent));
        assertEquals(0, client1.getActiveRents());
    }

    @Test
    void getRentTest() throws ParameterException {
        Rent rent = rm.addRent(client1, machine3);
        List<Rent> list = rm.findAll();
        assertTrue(list.contains(rent));
        assertEquals(rm.getRent(rent.getID()), rent);
    }
}
