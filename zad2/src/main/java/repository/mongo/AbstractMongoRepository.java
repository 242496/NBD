package repository.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import model.AdminMgd;
import model.AdvancedMgd;
import model.BasicMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;
import model.UniqueId;
import model.UniqueIdCodecProvider;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

public abstract class AbstractMongoRepository<T> implements AutoCloseable {

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

    public AbstractMongoRepository(String collectionName, Class<T> entityClass) {
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

    public void add(T object) {
    }

    public T findById(UniqueId id) {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        Bson filter = Filters.eq("_id", id);
        return collection.find().filter(filter).first();
    }

    public List<T> findAll() {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        return collection.find().into(new ArrayList<>());
    }

    public void update(T object) {
    }

    public void remove(UniqueId id) {
        MongoCollection<T> collection = mongoDatabase.getCollection(collectionName, entityClass);
        Bson filter = Filters.eq("_id", id);
        collection.deleteOne(filter);
    }

    @Override
    public void close(){
        mongoClient.close();
    }

}
