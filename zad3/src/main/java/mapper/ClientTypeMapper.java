package mapper;

import domain.Admin;
import domain.Advanced;
import domain.Basic;
import domain.ClientType;
import domain.Intermediate;
import mongo_model.AdminMgd;
import mongo_model.AdvancedMgd;
import mongo_model.BasicMgd;
import mongo_model.ClientTypeMgd;
import mongo_model.IntermediateMgd;
import redis_model.AdminRds;
import redis_model.AdvancedRds;
import redis_model.BasicRds;
import redis_model.ClientTypeRds;
import redis_model.IntermediateRds;

public class ClientTypeMapper {

    public static ClientTypeMgd toMongoDocument(ClientType clientType) {
        if (clientType.getClass() == Admin.class) {
            ClientTypeMgd clientTypeMgd = new AdminMgd();
            return clientTypeMgd;
        } else if (clientType.getClass() == Advanced.class) {
            ClientTypeMgd clientTypeMgd = new AdvancedMgd();
            return clientTypeMgd;
        } else if (clientType.getClass() == Intermediate.class) {
            ClientTypeMgd clientTypeMgd = new IntermediateMgd();
            return clientTypeMgd;
        } else if (clientType.getClass() == Basic.class) {
            ClientTypeMgd clientTypeMgd = new BasicMgd();
            return clientTypeMgd;
        }
        return null;
    }

    public static ClientTypeRds toRedisDocument(ClientType clientType) {
        if (clientType.getClass() == Admin.class) {
            ClientTypeRds clientTypeRds = new AdminRds();
            return clientTypeRds;
        } else if (clientType.getClass() == Advanced.class) {
            ClientTypeRds clientTypeRds = new AdvancedRds();
            return clientTypeRds;
        } else if (clientType.getClass() == Intermediate.class) {
            ClientTypeRds clientTypeRds = new IntermediateRds();
            return clientTypeRds;
        } else if (clientType.getClass() == Basic.class) {
            ClientTypeRds clientTypeRds = new BasicRds();
            return clientTypeRds;
        }
        return null;
    }

    public static ClientType toDomainModel(ClientTypeMgd clientTypeMgd) {
        if(clientTypeMgd.getClass() == AdminMgd.class) {
            ClientType clientType = new Admin();
            return clientType;
        } else if (clientTypeMgd.getClass() == AdvancedMgd.class) {
            ClientType clientType = new Advanced();
            return clientType;
        } else if (clientTypeMgd.getClass() == IntermediateMgd.class) {
            ClientType clientType = new Intermediate();
            return clientType;
        } else if (clientTypeMgd.getClass() == BasicMgd.class) {
            ClientType clientType = new Basic();
            return clientType;
        }
        return null;
    }

    public static ClientType toDomainModel(ClientTypeRds clientTypeRds) {
        if(clientTypeRds.getClass() == AdminRds.class) {
            ClientType clientType = new Admin();
            return clientType;
        } else if (clientTypeRds.getClass() == AdvancedRds.class) {
            ClientType clientType = new Advanced();
            return clientType;
        } else if (clientTypeRds.getClass() == IntermediateRds.class) {
            ClientType clientType = new Intermediate();
            return clientType;
        } else if (clientTypeRds.getClass() == BasicRds.class) {
            ClientType clientType = new Basic();
            return clientType;
        }
        return null;
    }
}
