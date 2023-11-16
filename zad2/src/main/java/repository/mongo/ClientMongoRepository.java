package repository.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.ClientMgd;
import model.RentMgd;
import org.bson.conversions.Bson;

public class ClientMongoRepository extends AbstractMongoRepository<ClientMgd> {


    public ClientMongoRepository() {
        super("clients", ClientMgd.class);
    }

    public ClientMgd findByUsername(String Username) {
        MongoCollection<ClientMgd> collection = mongoDatabase.getCollection(collectionName, ClientMgd.class);
        Bson filter = Filters.eq("username", Username);
        return collection.find().filter(filter).first();
    }

    public void add(ClientMgd client) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<ClientMgd> clientsCollection = mongoDatabase.getCollection(collectionName, ClientMgd.class);
            clientsCollection.insertOne(client);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }

    public void update(ClientMgd client) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<ClientMgd> clientsCollection = mongoDatabase.getCollection(collectionName, ClientMgd.class);
            Bson filter = Filters.eq("_id", client.getEntityId());

            Bson setUpdate = Updates.combine(
                    Updates.set("username", client.getUsername()),
                    Updates.set("type", client.getType()),
                    Updates.set("activerents", client.getActiveRents())
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
        MongoCollection<ClientMgd> collection = mongoDatabase.getCollection(collectionName, ClientMgd.class);
        collection.drop();
    }
}
