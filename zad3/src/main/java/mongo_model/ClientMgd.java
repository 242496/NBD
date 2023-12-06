package mongo_model;

import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class ClientMgd extends AbstractEntityMgd {
    @BsonProperty("username")
    private String Username;
    @BsonProperty("type")
    private ClientTypeMgd Type;
    @BsonProperty("activerents")
    private Integer ActiveRents = 0;

    @BsonCreator
    public ClientMgd(@BsonProperty("_id") UUID entityId,
                     @BsonProperty("username") String Username,
                     @BsonProperty("type") ClientTypeMgd Type,
                     @BsonProperty("activerents") Integer ActiveRents) {
        super(entityId);
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }

    public ClientMgd(@BsonProperty("username") String Username,
                     @BsonProperty("type") ClientTypeMgd Type,
                     @BsonProperty("activerents") Integer ActiveRents) {
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }

    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }
}
