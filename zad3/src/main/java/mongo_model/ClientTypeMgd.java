package mongo_model;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@ToString
@BsonDiscriminator(key = "_clazz")
public abstract class ClientTypeMgd extends AbstractEntityMgd {

    @BsonProperty("maxrents")
    protected int MaxRents;
    @BsonProperty("clientdiscount")
    protected double ClientDiscount;
    @BsonProperty("maxdays")
    protected int MaxDays;

    @BsonCreator
    public ClientTypeMgd(@BsonProperty("_id") UUID entityId,
                         @BsonProperty("maxrents") int MaxRents,
                         @BsonProperty("clientdiscount") double ClientDiscount,
                         @BsonProperty("maxdays") int MaxDays) {
        super(entityId);
        this.MaxRents = MaxRents;
        this.ClientDiscount = ClientDiscount;
        this.MaxDays = MaxDays;
    }

    public ClientTypeMgd() {
        this.MaxRents = getMaxRents();
        this.ClientDiscount = getClientDiscount();
        this.MaxDays = getMaxDays();
    }

    public double applyClientDiscount(double price) {
        return price;
    }
}
