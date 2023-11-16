package repository.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import mapper.RentMapper;
import model.ClientMgd;
import model.MachineMgd;
import model.RentMgd;
import org.bson.conversions.Bson;

public class RentMongoRepository extends AbstractMongoRepository<RentMgd> {


    public RentMongoRepository() {
        super("rents", RentMgd.class);
    }
    public RentMgd findByClient(ClientMgd client) {
        MongoCollection<RentMgd> collection = mongoDatabase.getCollection(collectionName, RentMgd.class);
        Bson filter = Filters.eq("client._id", client.getEntityId());
        return collection.find().filter(filter).first();
    }
    public RentMgd findByMachine(MachineMgd machine) {
        MongoCollection<RentMgd> collection = mongoDatabase.getCollection(collectionName, RentMgd.class);
        Bson filter = Filters.eq("machine._id", machine.getEntityId());
        return collection.find().filter(filter).first();
    }

    public void add(RentMgd rent) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            changeMachineState(clientSession, 1);
            MongoCollection<RentMgd> clientsCollection = mongoDatabase.getCollection(collectionName, RentMgd.class);
            clientsCollection.insertOne(rent);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }

    public void update(RentMgd rent) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<RentMgd> clientsCollection = mongoDatabase.getCollection(collectionName, RentMgd.class);
            Bson filter = Filters.eq("_id", rent.getEntityId());
            Bson setUpdate = Updates.combine(
                    Updates.set("client", rent.getClient()),
                    Updates.set("machine", rent.getMachine())
            );
            clientsCollection.updateOne(clientSession, filter, setUpdate);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }
    public void clearDatabase() {
        MongoCollection<RentMgd> collection = mongoDatabase.getCollection(collectionName, RentMgd.class);
        collection.drop();
    }

    private void changeMachineState(ClientSession clientSession, Integer number) {

        MongoCollection<MachineMgd> collection = this.mongoDatabase.getCollection("machines", MachineMgd.class);
        Bson filter = Filters.eq("_id", number);
        Bson update;

        update = Updates.inc("isRented", 1);

        collection.updateOne(clientSession, filter, update);
    }
}
