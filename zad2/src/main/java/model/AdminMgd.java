package model;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Getter
@BsonDiscriminator(key = "_clazz", value = "admin")
public class AdminMgd extends ClientTypeMgd {

    @BsonCreator
    public AdminMgd() { }

//    @JsonCreator
//    public Admin(@JsonProperty("_id") UniqueId entityId,
//                 @JsonProperty("maxrents") int MaxRents,
//                 @JsonProperty("clientdiscount") double ClientDiscount,
//                 @JsonProperty("maxdays") int MaxDays) {
//        super(entityId, MaxRents, ClientDiscount, MaxDays);
//    }

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = 0.0;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() {
        return 1000;
    }

    @Override
    public int getMaxDays() { return 365; }
}
