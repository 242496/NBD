package repository.redis;

import redis_model.MachineRds;
import repository.AbstractRedisRepository;

public class MachineRedisRepository extends AbstractRedisRepository<MachineRds> {
    public MachineRedisRepository() {
        super(MachineRds.class, "machine:");
    }
}