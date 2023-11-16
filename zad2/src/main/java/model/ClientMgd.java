package model;

import domain.ClientType;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClientMgd extends AbstractEntityMgd {
    @BsonProperty("username")
    private String Username;
    @BsonProperty("type")
    private ClientTypeMgd Type;
    @BsonProperty("activerents")
    private Integer ActiveRents = 0;

    @BsonCreator
    public ClientMgd(@BsonProperty("_id") UniqueId entityId,
                     @BsonProperty("username") String Username,
                     @BsonProperty("type") ClientTypeMgd Type,
                     @BsonProperty("activerents") Integer ActiveRents) {
        super(entityId);
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }

    public ClientMgd(String Username, ClientTypeMgd Type) {
        super(new UniqueId(UUID.randomUUID()));
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("username='").append(Username).append('\'');
        sb.append(", type='").append(Type).append('\'');
        sb.append(", activerents='").append(ActiveRents);
        sb.append('}');
        return sb.toString();
    }

    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }
}
