package repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mongo_model.AbstractEntityMgd;
import mongo_model.AdminMgd;
import mongo_model.AdvancedMgd;
import mongo_model.BasicMgd;
import mongo_model.ClientTypeMgd;
import mongo_model.IntermediateMgd;
import mongo_model.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

public abstract class AbstractMongoRepository<T extends AbstractEntityMgd> implements Repository<T> {

    protected ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017,localhost:27018,localhost:27019/?replicaSet=replica_set_single");
    protected MongoCredential credential = MongoCredential.createCredential("admin", "admin", "adminpassword".toCharArray());
    protected CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .register(ClientTypeMgd.class, AdvancedMgd.class, AdminMgd.class, IntermediateMgd.class, BasicMgd.class)
            .conventions(Conventions.DEFAULT_CONVENTIONS)
            .build());
    protected MongoClient mongoClient;
    protected MongoDatabase mongoDatabase;
    protected final String collectionName;
    private final Class<T> entityClass;

    public AbstractMongoRepository(Class<T> entityClass, String collectionName) {
        this.collectionName = collectionName;
        this.entityClass = entityClass;
        initDbConnection();
    }

    protected void initDbConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new UniqueIdCodecProvider()),
                        MongoClientSettings.getDefaultCodecRegistry(),
                        pojoCodecRegistry)
                ).build();
    mongoClient = MongoClients.create(settings);
    mongoDatabase = mongoClient.getDatabase("machine_rent");
    }

    @Override
    public T getById(UUID id) {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        Bson filter = Filters.eq("_id", id);
        return collection.find().filter(filter).first();
    }

    @Override
    public List<T> findAll() {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T add(T object) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<T> clientsCollection = mongoDatabase.getCollection(collectionName, entityClass);
            clientsCollection.insertOne(object);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
        return object;
    }

    @Override
    public T update(T updatedEntity) {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        Bson filter = Filters.eq("_id", updatedEntity.getEntityId());
        collection.findOneAndReplace(filter, updatedEntity);
        return updatedEntity;
    }

    public void remove(UUID id) {
        ClientSession clientSession = mongoClient.startSession();
        try {
            clientSession.startTransaction();
            MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
            Bson filter = Filters.eq("_id", id);
            collection.deleteOne(filter);
            clientSession.commitTransaction();
        } catch (Exception e) {
            clientSession.abortTransaction();
        } finally {
            clientSession.close();
        }
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public void close(){
        mongoClient.close();
        mongoDatabase.drop();
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        collection.drop();
    }
}
