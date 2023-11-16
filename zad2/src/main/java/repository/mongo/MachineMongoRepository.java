package repository.mongo;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.ValidationOptions;
import model.MachineMgd;
import model.RentMgd;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MachineMongoRepository extends AbstractMongoRepository<MachineMgd> {

    public MachineMongoRepository() {
        super("machines", MachineMgd.class);

        MongoIterable<String> list = this.mongoDatabase.listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                mongoDatabase.getCollection(collectionName).drop();
            }
        }
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                            $jsonSchema:{
                                "bsonType": "object",
                                "required": [ "CPUs", "RAM", "Disk", "System", "isRented" ]
                                "properties": {
                                   "isRented": {
                                       "bsonType": "int",
                                       "minimum" : 0,
                                       "maximum" : 1
                                       "description": "must be 1 for rented and 0 for available"
                                   }
                                }
                            }
                        }
                        """)
        );
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);
        super.mongoDatabase.createCollection("machines", createCollectionOptions);
    }

    public void add(MachineMgd machine) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<MachineMgd> collection = mongoDatabase.getCollection(collectionName, MachineMgd.class);
            collection.insertOne(machine);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }

    public void update(MachineMgd machine) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<MachineMgd> machinesCollection = mongoDatabase.getCollection(collectionName, MachineMgd.class);
            Bson filter = Filters.eq("_id", machine.getEntityId());

            Bson setUpdate = Updates.combine(
                    Updates.set("cpus", machine.getCPUs()),
                    Updates.set("ram", machine.getRAM()),
                    Updates.set("disk", machine.getDisk()),
                    Updates.set("system", machine.getSystem()),
                    Updates.set("isrented", machine.getIsRented())
            );

            machinesCollection.updateOne(clientSession, filter, setUpdate);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }

    public void clearDatabase() {
        MongoCollection<MachineMgd> collection = mongoDatabase.getCollection(collectionName, MachineMgd.class);
        collection.drop();
    }
}
