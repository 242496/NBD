package repository.redis;

import redis_model.RentRds;
import repository.AbstractRedisRepository;

public class RentRedisRepository extends AbstractRedisRepository<RentRds> {
    public RentRedisRepository() {
        super(RentRds.class, "rent:");
    }
}
