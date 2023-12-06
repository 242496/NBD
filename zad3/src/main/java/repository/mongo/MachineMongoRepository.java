package repository.mongo;

import mongo_model.MachineMgd;
import repository.AbstractMongoRepository;

public class MachineMongoRepository extends AbstractMongoRepository<MachineMgd> {
    public MachineMongoRepository() {
        super(MachineMgd.class, "machine:");
    }
}
