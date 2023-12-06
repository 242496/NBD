package repository.domain;

import domain.Rent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mapper.RentMapper;
import mongo_model.RentMgd;
import redis_model.RentRds;
import repository.Repository;
import repository.mongo.RentMongoRepository;
import repository.redis.RentRedisRepository;

public class RentRepository implements Repository<Rent> {

    private RentMongoRepository rentMongoRepository;
    private RentRedisRepository rentRedisRepository;


    public RentRepository(RentMongoRepository rentMongoRepository, RentRedisRepository rentRedisRepository) {
        this.rentMongoRepository = rentMongoRepository;
        this.rentRedisRepository = rentRedisRepository;
    }

    @Override
    public Rent add(Rent entity) throws Exception {
        rentRedisRepository.add(RentMapper.toRedisDocument(entity));
        return RentMapper.toDomainModel(rentMongoRepository.add(RentMapper.toMongoDocument(entity)));
    }

    @Override
    public Rent getById(UUID id) throws Exception {
        if (rentRedisRepository.checkConnection()){
            return RentMapper.toDomainModel(rentRedisRepository.getById(id));
        } else {
            return RentMapper.toDomainModel(rentMongoRepository.getById(id));
        }
    }
    @Override
    public void remove(UUID id){
        rentRedisRepository.remove(id);
        rentMongoRepository.remove(id);
    }

    @Override
    public Rent update(Rent rent) throws Exception {
        rentRedisRepository.update(RentMapper.toRedisDocument(rent));
        return RentMapper.toDomainModel(rentMongoRepository.update(RentMapper.toMongoDocument(rent)));
    }

    @Override
    public List<Rent> findAll() throws Exception {
        List<Rent> rents = new ArrayList<>();
        if (rentRedisRepository.checkConnection()) {
            List<RentRds> found = rentRedisRepository.findAll();
            for (RentRds rentRds: found) {
                rents.add(RentMapper.toDomainModel(rentRds));
            }
        } else {
            List<RentMgd> found = rentMongoRepository.findAll();
            for (RentMgd rentMgd: found) {
                rents.add(RentMapper.toDomainModel(rentMgd));
            }
        }
        return rents;
    }

    @Override
    public long size() throws Exception {
        return findAll().size();
    }

    @Override
    public void close() throws Exception {
        rentMongoRepository.close();
        rentRedisRepository.close();
    }
}

