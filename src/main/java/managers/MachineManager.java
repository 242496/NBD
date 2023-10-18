package managers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import model.Machine;
import repository.MachineRepository;

public class MachineManager {
    private final MachineRepository machineRepository;
    private final EntityManager em;
    private EntityTransaction et;

    public MachineManager(EntityManager entityManager) {
        em = entityManager;
        machineRepository = new MachineRepository(entityManager);
    }

    public void removeMachine(long ID) {
        et = em.getTransaction();
        et.begin();
        machineRepository.remove(machineRepository.getByID(ID));
        et.commit();
    }

    public List<Machine> findAll() {
        et = em.getTransaction();
        et.begin();
        List<Machine> list = machineRepository.findAll();
        et.commit();
        return list;
    }

    public Machine getMachine(long ID) {
        et = em.getTransaction();
        et.begin();
        Machine machine = machineRepository.getByID(ID);
        et.commit();
        return machine;
    }

    public Machine addMachine(int CPUs, int RAM, int Disk, Machine.SystemType System, boolean isRented) {
        Machine machine = new Machine(CPUs, RAM, Disk, System, isRented);
        et = em.getTransaction();
        et.begin();
        machineRepository.add(machine);
        et.commit();
        return machine;
    }
}
