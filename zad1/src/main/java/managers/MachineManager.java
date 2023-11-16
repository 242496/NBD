package managers;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import model.Machine;
import repository.MachineRepository;

public class MachineManager {
    private final MachineRepository machineRepository;
    private final EntityManager em;

    public MachineManager(EntityManager entityManager) {
        em = entityManager;
        machineRepository = new MachineRepository(entityManager);
    }

    public void removeMachine(UUID ID) {
        machineRepository.remove(machineRepository.getByID(ID));
    }

    public List<Machine> findAll() {
        List<Machine> list = machineRepository.findAll();
        return list;
    }

    public Machine addMachine(int CPUs, int RAM, int Disk, Machine.SystemType System, boolean isRented) {
        Machine machine = new Machine(CPUs, RAM, Disk, System, isRented);
        machineRepository.add(machine);
        return machine;
    }
}
