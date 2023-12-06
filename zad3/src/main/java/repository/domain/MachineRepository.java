package repository.domain;

import domain.Machine;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mapper.MachineMapper;
import mongo_model.MachineMgd;
import redis_model.MachineRds;
import repository.Repository;
import repository.mongo.MachineMongoRepository;
import repository.redis.MachineRedisRepository;

public class MachineRepository implements Repository<Machine> {
    private final MachineMongoRepository machineMongoRepository;
    private final MachineRedisRepository machineRedisRepository;

    public MachineRepository(MachineMongoRepository machineMongoRepository, MachineRedisRepository machineRedisRepository) {
        this.machineMongoRepository = machineMongoRepository;
        this.machineRedisRepository = machineRedisRepository;
    }

    @Override
    public Machine add(Machine entity) {
        machineRedisRepository.add(MachineMapper.toRedisDocument(entity));
        return MachineMapper.toDomainModel(machineMongoRepository.add(MachineMapper.toMongoDocument(entity)));
    }

    @Override
    public Machine getById(UUID id){
        if (machineRedisRepository.checkConnection()){
            return MachineMapper.toDomainModel(machineRedisRepository.getById(id));
        } else {
            return MachineMapper.toDomainModel(machineMongoRepository.getById(id));
        }
    }

    @Override
    public void remove(UUID id){
        machineRedisRepository.remove(id);
        machineMongoRepository.remove(id);
    }

    @Override
    public Machine update(Machine machine){
        machineRedisRepository.update(MachineMapper.toRedisDocument(machine));
        return MachineMapper.toDomainModel(machineMongoRepository.update(MachineMapper.toMongoDocument(machine)));
    }

    @Override
    public List<Machine> findAll(){
        List<Machine> machines = new ArrayList<>();
        if (machineRedisRepository.checkConnection()) {
            List<MachineRds> found = machineRedisRepository.findAll();
            for (MachineRds machineRds: found) {
                machines.add(MachineMapper.toDomainModel(machineRds));
            }
        } else {
            List<MachineMgd> found = machineMongoRepository.findAll();
            for (MachineMgd machineMgd: found) {
                machines.add(MachineMapper.toDomainModel(machineMgd));
            }
        }
        return machines;
    }

    @Override
    public long size(){
        return findAll().size();
    }

    @Override
    public void close() throws Exception {
        machineMongoRepository.close();
        machineRedisRepository.close();
    }
}
