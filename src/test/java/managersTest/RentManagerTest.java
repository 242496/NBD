package managersTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.RollbackException;
import java.util.List;
import managers.RentManager;
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
    private static EntityManager em1;
    private static EntityTransaction et;
    private static EntityTransaction et1;
    private static RentManager rm;
    private static RentManager rm1;
    private static Client client1, client2, client3, client4;
    private static Machine machine1, machine2, machine3;
    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        em1 = emf.createEntityManager();
        rm = new RentManager(em);
        rm1 = new RentManager(em1);
        et = em.getTransaction();
        et1 = em1.getTransaction();

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
    void addRentTest() throws Exception {
        et.begin();
        Rent rent = rm.addRent(client1, machine3);
        et.commit();

        et.begin();
        List<Rent> list = rm.findAll();
        et.commit();

        assertTrue(list.contains(rent));
        assertEquals(1, client1.getActiveRents());

        et.begin();
        rm.removeRent(rent.getID());
        et.commit();
    }

    @Test
    void rentsLimitTest() throws Exception {
        Basic basic = new Basic();
        int activeRents = client3.getActiveRents();
        assertEquals(1, basic.getMaxRents());
        assertEquals(0, activeRents);

        et.begin();
        Rent rent = rm.addRent(client3, machine3);
        et.commit();

        activeRents = client3.getActiveRents();
        assertEquals(1, activeRents);

        et.begin();
        assertThrows(Exception.class, ()->rm.addRent(client3, machine2));
        et.commit();

        et.begin();
        rm.removeRent(rent.getID());
        et.commit();
    }

    @Test
    void alreadyRentedMachineTest() throws Exception {
        assertFalse(machine1.isRented());

        et.begin();
        Rent rent = rm.addRent(client4, machine1);
        et.commit();

        assertTrue(machine1.isRented());

        et.begin();
        assertThrows(Exception.class, ()->rm.addRent(client3, machine1));
        et.commit();
    }

    @Test
    void removeRentTest() throws Exception {
        et.begin();
        Rent rent = rm.addRent(client1, machine1);
        et.commit();

        et.begin();
        List<Rent> list = rm.findAll();
        et.commit();

        assertTrue(list.contains(rent));
        assertEquals(1, client1.getActiveRents());

        et.begin();
        rm.removeRent(rent.getID());
        et.commit();

        et.begin();
        list = rm.findAll();
        et.commit();

        assertFalse(list.contains(rent));
        assertEquals(0, client1.getActiveRents());
    }

    @Test
    void getRentTest() throws Exception {
        et.begin();
        Rent rent = rm.addRent(client1, machine3);
        et.commit();

        et.begin();
        List<Rent> list = rm.findAll();
        et.commit();

        assertTrue(list.contains(rent));

        et.begin();
        assertEquals(rm.getRent(rent.getID()), rent);
        et.commit();
    }

    @Test
    void OptimisticLockTest() throws Exception {
        em.getTransaction().begin();
        em1.getTransaction().begin();

        Client client_A = em.find(Client.class, client1.getID(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        Machine machine_A = em.find(Machine.class, machine1.getID());

        Rent rent1 = new Rent(client_A, machine_A);

        Client client_B = em1.find(Client.class, client1.getID(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        Machine machine_B = em1.find(Machine.class, machine2.getID());

        Rent rent2 = new Rent(client_B, machine_B);

        em.persist(rent1);
        em1.persist(rent2);

        assertDoesNotThrow(() -> et.commit());
        RollbackException rollbackException = assertThrows(RollbackException.class, () -> et1.commit());
        assertEquals(OptimisticLockException.class, rollbackException.getCause().getClass());
    }
}
