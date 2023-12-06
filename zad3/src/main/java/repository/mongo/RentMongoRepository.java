package repository.mongo;

import mongo_model.RentMgd;
import repository.AbstractMongoRepository;


public class RentMongoRepository extends AbstractMongoRepository<RentMgd> {
    public RentMongoRepository() {
        super(RentMgd.class, "rent:");
    }
}
