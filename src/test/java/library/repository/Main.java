package library.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import managers.ClientManager;
import model.Admin;
import model.Advanced;
import model.Basic;
import model.Client;
import model.ClientType;
import model.Intermediate;
import model.Machine;
import model.Rent;
import org.apache.avalon.framework.parameters.ParameterException;

public class Main {
    public static void main(String[] args) throws ParameterException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        ClientManager cm = new ClientManager(em);


        Intermediate intermediate = new Intermediate();
        Client client1 = new Client("SzymonP", intermediate);
        Machine machine1 = new Machine(2, 256, 200, Machine.SystemType.WINDOWS10);


        Advanced advanced = new Advanced();
        Client client2 = new Client("MichalK", advanced);
        Machine machine2 = new Machine(3, 512, 250, Machine.SystemType.WINDOWS7);

        Basic basic = new Basic();
        Client client3 = new Client("Podgor", basic);

        Client client4 = new Client("White115", intermediate);

        Rent rent1 = new Rent(client1, machine1);
        Rent rent2 = new Rent(client2, machine2);
        Rent rent3 = new Rent(client3, machine1);
        Rent rent4 = new Rent(client4, machine1);
        Rent rent5 = new Rent(client1, machine2);


        em.getTransaction().begin();
        em.persist(rent1);
        em.persist(rent2);
        em.persist(rent3);
        em.persist(rent4);
        em.persist(rent5);
        em.getTransaction().commit();


        //Tests

        List<Client> list = cm.findAll();
        assertEquals(list.size(),4);
        assertTrue(list.contains(client1));
        assertTrue(list.contains(client2));
        assertTrue(list.contains(client3));
        assertTrue(list.contains(client4));


        emf.close();
    }
}