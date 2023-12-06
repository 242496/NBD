package mapper;

import domain.Rent;
import java.util.UUID;
import mongo_model.RentMgd;
import redis_model.RentRds;

public class RentMapper {
    private static ClientMapper clientMapper;
    private static MachineMapper machineMapper;

    public RentMapper() {
        clientMapper = new ClientMapper();
        machineMapper = new MachineMapper();
    }

    public static RentMgd toMongoDocument(Rent rent) {
        RentMgd rentMgd = new RentMgd();
        rentMgd.setClient(clientMapper.toMongoDocument(rent.getClient()));
        rentMgd.setMachine(machineMapper.toMongoDocument(rent.getMachine()));
        rentMgd.setEntityId(rent.getEntityId());
        rentMgd.setBeginTime(rent.getBeginTime());
        rentMgd.setEndTime(rent.getEndTime());
        return rentMgd;
    }

    public static RentRds toRedisDocument(Rent rent) {
        if (rent.getEntityId() == null) {
            rent.setEntityId(UUID.randomUUID());
        }
        RentRds rentRds = new RentRds();
        rentRds.setClient(clientMapper.toRedisDocument(rent.getClient()));
        rentRds.setMachine(machineMapper.toRedisDocument(rent.getMachine()));
        rentRds.setEntityId(rent.getEntityId());
        rentRds.setBeginTime(rent.getBeginTime());
        rentRds.setEndTime(rent.getEndTime());
        return rentRds;
    }

    public static Rent toDomainModel(RentMgd rentMgd) throws Exception {
        Rent rent = new Rent(
                clientMapper.toDomainModel(rentMgd.getClient()),
                machineMapper.toDomainModel(rentMgd.getMachine())
        );
        rent.setBeginTime(rentMgd.getBeginTime());
        rent.setEndTime(rentMgd.getEndTime());
        return rent;
    }

    public static Rent toDomainModel(RentRds rentRds) throws Exception {
        Rent rent = new Rent(
                clientMapper.toDomainModel(rentRds.getClient()),
                machineMapper.toDomainModel(rentRds.getMachine())
        );
        rent.setBeginTime(rentRds.getBeginTime());
        rent.setEndTime(rentRds.getEndTime());
        return rent;
    }
}
