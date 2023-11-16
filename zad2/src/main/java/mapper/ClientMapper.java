package mapper;

import domain.Client;
import model.ClientMgd;

public class ClientMapper {
    public ClientMgd toMongoDocument(Client client) {
        if (client.getClass() == Client.class) {
            ClientMgd clientMgd = new ClientMgd(
                    client.getEntityId(),
                    client.getUsername(),
                    client.getType(),
                    client.getActiveRents()
            );
            return clientMgd;
        }
        return null;
    }
    public Client toDomainModel(ClientMgd clientMgd) {
        if(clientMgd.getClass() == ClientMgd.class) {
            Client client = new Client(
                    clientMgd.getUsername(),
                    clientMgd.getType()
            );
            return client;
        }
        return null;
    }


//    public static ClientMgd toMongoDocument(Client client) {
//        return ClientMgd.builder()
//                .entityId(client.getEntityId())
//                .Username(client.getUsername())
//                .Type(client.getType())
//                .ActiveRents(client.getActiveRents())
//                .build();
//    }
//
//    public static Client toDomainModel(ClientMgd clientMgd){
//        return Client.builder()
//                .entityId(clientMgd.getEntityId())
//                .Username(clientMgd.getUsername())
//                .Type(clientMgd.getType())
//                .ActiveRents(clientMgd.getActiveRents())
//                .build();
//    }
}
