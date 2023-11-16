package model;


import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@BsonDiscriminator(key = "_clazz")
public abstract class ClientTypeMgd extends AbstractEntityMgd {

    @BsonProperty("maxrents")
    protected int MaxRents;
    @BsonProperty("clientdiscount")
    protected double ClientDiscount;
    @BsonProperty("maxdays")
    protected int MaxDays;

    @BsonCreator
    public ClientTypeMgd(@BsonProperty("_id") UniqueId entityId,
                         @BsonProperty("maxrents") int MaxRents,
                         @BsonProperty("clientdiscount") double ClientDiscount,
                         @BsonProperty("maxdays") int MaxDays) {
        super(entityId);
        this.MaxRents = MaxRents;
        this.ClientDiscount = ClientDiscount;
        this.MaxDays = MaxDays;
    }

    public ClientTypeMgd() {
        super(new UniqueId(UUID.randomUUID()));
        this.MaxRents = getMaxRents();
        this.ClientDiscount = getClientDiscount();
        this.MaxDays = getMaxDays();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientType{");
        sb.append("maxrents='").append(MaxRents).append('\'');
        sb.append(", clientdiscount='").append(ClientDiscount).append('\'');
        sb.append(", maxdays='").append(MaxDays);
        sb.append('}');
        return sb.toString();
    }

    public double applyClientDiscount(double price) {
        return price;
    }
}
