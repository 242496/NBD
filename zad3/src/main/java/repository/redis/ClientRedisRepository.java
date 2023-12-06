package repository.redis;

import redis_model.ClientRds;
import repository.AbstractRedisRepository;

public class ClientRedisRepository extends AbstractRedisRepository<ClientRds> {
    public ClientRedisRepository() {
        super(ClientRds.class, "client:");
    }
}
