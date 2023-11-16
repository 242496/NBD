package mapper;

import domain.Admin;
import domain.Advanced;
import domain.Basic;
import domain.Client;
import domain.ClientType;
import domain.Intermediate;
import model.AdminMgd;
import model.AdvancedMgd;
import model.BasicMgd;
import model.ClientMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;

public class ClientTypeMapper {

    public ClientTypeMgd toMongoDocument(ClientType clientType) {
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
    public ClientType toDomainModel(ClientTypeMgd clientTypeMgd) {
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
}
