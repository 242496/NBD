package managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.Persistence;
import java.util.List;
import model.Admin;
import model.Advanced;
import model.Basic;
import model.Client;
import model.Intermediate;
import model.Machine;
import model.Rent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientManagerTest {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ClientManager cm;
    private static Basic basic;
    private static Intermediate intermediate;
    private static Advanced advanced;
    private static Admin admin;
    private static Client client1, client2, client3, client4;
    private static Machine machine1, machine2;

    @BeforeAll
    static void BeforeAll() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
        cm = new ClientManager(em);

        basic = new Basic();
        intermediate = new Intermediate();
        advanced = new Advanced();
        admin = new Admin();

        client1 = new Client("SzymonP", intermediate);
        client2 = new Client("MichalK", advanced);
        client3 = new Client("Podgor", basic);
        client4 = new Client("White115", intermediate);

        machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);
        machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);

//        Rent rent1 = new Rent(client1, machine1);
//        Rent rent2 = new Rent(client2, machine2);
//        Rent rent3 = new Rent(client3, machine1);
//        Rent rent4 = new Rent(client4, machine1);
//        Rent rent5 = new Rent(client1, machine2);

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
    void tempTestAll() {
        List<Client> list = cm.findAll();
        assertEquals(list.size(),4);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client3));
        assertTrue(list.contains(client4));

        Client client5 = cm.addClient("MariuszPostol", advanced);
        list = cm.findAll();
        assertEquals(list.size(),5);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client3));
        assertTrue(list.contains(client4));
        assertTrue(list.contains(client5));
        assertEquals(5, client5.getID());
        assertEquals(cm.getClient(5), client5);

        cm.removeClient(3);
        list = cm.findAll();
        assertEquals(list.size(),4);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client4));
        assertTrue(list.contains(client5));
    }
}
