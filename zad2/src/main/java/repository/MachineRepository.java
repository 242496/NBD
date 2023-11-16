package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.MachineMgd;
import model.UniqueId;
import repository.mongo.MachineMongoRepository;

public class MachineRepository implements Repository<MachineMgd> {
    private final MachineMongoRepository machineMongoRepository;

    public MachineRepository(MachineMongoRepository machineMongoRepository) {
        this.machineMongoRepository = machineMongoRepository;
    }

    @Override
    public MachineMgd add(MachineMgd entity) {
        machineMongoRepository.add(entity);
        return entity;
    }


    public MachineMgd getById(UniqueId id) {
        return machineMongoRepository.findById(id);
    }

    @Override
    public void remove(UniqueId id) {
        machineMongoRepository.remove(id);
    }

    @Override
    public void update(MachineMgd entity) {
        machineMongoRepository.update(entity);
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<MachineMgd> findAll() {
        List<MachineMgd> machines = new ArrayList<>();
        List<MachineMgd> found = machineMongoRepository.findAll();
        machines.addAll(found);
        return machines;
    }
}
