package repository.mongo;

import mongo_model.ClientMgd;
import repository.AbstractMongoRepository;

public class ClientMongoRepository extends AbstractMongoRepository<ClientMgd> {
    public ClientMongoRepository() { super(ClientMgd.class, "client:"); }
}
