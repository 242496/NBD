package managers;

import domain.Client;
import domain.Machine;
import java.util.ArrayList;
import java.util.List;
import mapper.MachineMapper;
import model.ClientMgd;
import model.MachineMgd;
import model.RentMgd;
import model.UniqueId;
import repository.MachineRepository;
import repository.mongo.MachineMongoRepository;

public class MachineManager {

    private final MachineRepository machineRepository;
    private MachineMapper machineMapper;

    public MachineManager() {
        this.machineRepository = new MachineRepository(new MachineMongoRepository());
        this.machineMapper = new MachineMapper();
    }

    public List<Machine> findAllMachines() {
        List<MachineMgd> machinesList = machineRepository.findAll();
        List<Machine> list = new ArrayList<Machine>();
        for(MachineMgd m :machinesList) {
            list.add(machineMapper.toDomainModel(m));
        }
        return list;
    }

    public MachineMgd addMachine(MachineMgd machine) {
        machineRepository.add(machine);
        machine.setIsRented(false);
        return machine;
    }

    public void removeMachine(UniqueId ID) {
        machineRepository.remove(ID);
    }
}
