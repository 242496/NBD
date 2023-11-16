package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.ClientMgd;
import model.MachineMgd;
import model.RentMgd;
import model.UniqueId;
import repository.mongo.RentMongoRepository;

public class RentRepository implements Repository<RentMgd> {

    private RentMongoRepository rentMongoRepository;


    public RentRepository(RentMongoRepository rentMongoRepository) {
        this.rentMongoRepository = rentMongoRepository;
    }

    public RentMgd findByMachine(MachineMgd machine) {
        return rentMongoRepository.findByMachine(machine);
    }

    public RentMgd findByClient(ClientMgd client) {
        return rentMongoRepository.findByClient(client);
    }

    @Override
    public RentMgd add(RentMgd entity) {
        rentMongoRepository.add(entity);
        return entity;
    }

    @Override
    public RentMgd getById(UniqueId id) {
        return rentMongoRepository.findById(id);
    }

    @Override
    public void remove(UniqueId id) {
        rentMongoRepository.remove(id);
    }

    @Override
    public void update(RentMgd entity) {
        rentMongoRepository.update(entity);
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<RentMgd> findAll() {
        List<RentMgd> rents = new ArrayList<>();
        List<RentMgd> found = rentMongoRepository.findAll();
        rents.addAll(found);
        return rents;
    }
}

