package repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import model.Rent;
import org.apache.avalon.framework.parameters.ParameterException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.RentRepository;

public class RentRepositoryTest {
    private static EntityManager em;
    private static RentRepository rr;
    private static Client client1, client2, client3, client4;
    private static Machine machine1, machine2;
    @BeforeAll
    static void BeforeAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRES_MACHINE_RENT");
        em = emf.createEntityManager();
        rr = new RentRepository(em);

        Basic basic = new Basic();
        Intermediate intermediate = new Intermediate();
        Advanced advanced = new Advanced();

        client1 = new Client("SzymonP", intermediate);
        client2 = new Client("MichalK", advanced);
        client3 = new Client("Podgor", basic);
        client4 = new Client("White115", intermediate);

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
    void addTest() throws Exception {
        Rent rent1 = new Rent(client1, machine1);
        rr.add(rent1);
        assertTrue(em.contains(rent1));
        Rent rent2 = new Rent(client1, machine2);
        rr.add(rent2);
        assertNotNull(rr.getByID(rent2.getID()));
        assertTrue(em.contains(rent2));
    }

    @Test
    void removeTest() throws Exception {
        Rent rent1 = new Rent(client1, machine1);
        rr.add(rent1);
        assertTrue(em.contains(rent1));
        rr.remove(rent1);
        assertFalse(em.contains(rent1));
    }

    @Test
    void findAllTest() throws Exception{
        Rent rent3 = new Rent(client3, machine1);
        Rent rent4 = new Rent(client4, machine2);
        rr.add(rent3);
        rr.add(rent4);
        assertTrue(em.contains(rent3));
        assertTrue(em.contains(rent4));
        em.getTransaction().begin();
        em.persist(rent3);
        em.persist(rent4);
        em.getTransaction().commit();
        List<Rent> list = rr.findAll();
        assertEquals(4,list.size());
    }
}
