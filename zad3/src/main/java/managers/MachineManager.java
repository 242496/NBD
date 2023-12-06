package managers;

import domain.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import repository.domain.MachineRepository;
import repository.mongo.MachineMongoRepository;
import repository.redis.MachineRedisRepository;

public class MachineManager {

    private final MachineRepository machineRepository;

    public MachineManager() {
        this.machineRepository = new MachineRepository(new MachineMongoRepository(), new MachineRedisRepository());
    }

    public List<Machine> findAllMachines() {
        List<Machine> list = new ArrayList<>();
        machineRepository.findAll().stream().forEach(machine -> list.add(machine));
        return list;
    }

    public Machine addMachine(Machine machine) {
        machineRepository.add(machine);
        machine.setRented(false);
        return machine;
    }

    public void removeMachine(UUID id) {
        machineRepository.remove(id);
    }
}
