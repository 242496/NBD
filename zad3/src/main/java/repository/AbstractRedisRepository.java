package repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;
import redis_model.AbstractEntityRds;

public abstract class AbstractRedisRepository<T extends AbstractEntityRds> implements Repository<T> {
    private static JedisPooled pool;
    private static Jsonb jsonb = JsonbBuilder.create();
    protected Class<T> entityClass;
    private String prefix;

    Properties prop = new Properties();
    InputStream stream;
    {
        try {
            stream = new FileInputStream("src/main/resources/connection.properties");
            prop.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPooled(new HostAndPort(prop.getProperty("server_name"), Integer.parseInt(prop.getProperty("port"))), clientConfig);
    }

    public AbstractRedisRepository(Class<T> entityClass, String prefix) {
        this.entityClass = entityClass;
        this.prefix = prefix;
        initConnection();
    }

    public boolean checkConnection() {
        return pool.getPool().getResource().isConnected();
    }

    public void clearCache(){
        Set<String> keys = pool.keys("*");
        for (String key : keys){
            pool.del(key);
        }
    }

    public void clearThis(){
        Set<String> keys = pool.keys(prefix + "*");
        for (String key : keys){
            pool.del(key);
        }
    }

    @Override
    public T getById(UUID id) {
        String found = pool.get(prefix + id.toString());
        T foundObj = jsonb.fromJson(found, this.entityClass);
        return foundObj;
    }

    @Override
    public T add(T entity) {
        String entityStr = jsonb.toJson(entity);
        pool.set(prefix + entity.getEntityId().toString(), entityStr);

        return entity;
    }

    @Override
    public void remove(UUID id) {
        pool.del(prefix + id.toString());
    }

    @Override
    public T update(T updatedEntity) {
        String entityStr = jsonb.toJson(updatedEntity);
        pool.set(prefix + updatedEntity.getEntityId().toString(), entityStr);

        return updatedEntity;
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<T> findAll() {
        List<T> all = new ArrayList<>();
        Set<String> keys = pool.keys(prefix + "*");
        for (String key : keys){
            all.add(jsonb.fromJson(pool.get(key), this.entityClass));
        }

        return all;
    }

    @Override
    public void close() throws Exception {
        pool.getPool().destroy();
        pool.close();
    }
}
