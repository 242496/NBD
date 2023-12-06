package mapper;

import domain.Client;
import java.util.UUID;
import mongo_model.ClientMgd;
import redis_model.ClientRds;

public class ClientMapper {
    public static ClientMgd toMongoDocument(Client client) {
        if (client.getClass() == Client.class) {
            ClientMgd clientMgd = new ClientMgd(
                    client.getEntityId(),
                    client.getUsername(),
                    client.getTypeMgd(),
                    client.getActiveRents()
            );
            return clientMgd;
        }
        return null;
    }

    public static ClientRds toRedisDocument(Client client) {
        if (client.getEntityId() == null) {
            client.setEntityId(UUID.randomUUID());
        }
        if (client.getClass() == Client.class) {
            ClientRds clientRds = new ClientRds(
                    client.getEntityId(),
                    client.getUsername(),
                    client.getTypeRds(),
                    client.getActiveRents()
            );
            return clientRds;
        }
        return null;
    }

    public static Client toDomainModel(ClientMgd clientMgd) {
        if(clientMgd.getClass() == ClientMgd.class) {
            Client client = new Client(
                    clientMgd.getUsername(),
                    clientMgd.getType()
            );
            return client;
        }
        return null;
    }

    public static Client toDomainModel(ClientRds clientRds) {
        if(clientRds.getClass() == ClientRds.class) {
            Client client = new Client(
                    clientRds.getUsername(),
                    clientRds.getType()
            );
            return client;
        }
        return null;
    }
}
