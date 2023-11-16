package model;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Getter
@BsonDiscriminator(key = "_clazz",value = "basic")
public class BasicMgd extends ClientTypeMgd {
    @BsonCreator
    public BasicMgd() { }

//    @JsonCreator
//    public Basic(@JsonProperty("_id") UniqueId entityId,
//                        @JsonProperty("maxrents") int MaxRents,
//                        @JsonProperty("clientdiscount") double ClientDiscount,
//                        @JsonProperty("maxdays") int MaxDays) {
//        super(entityId, MaxRents, ClientDiscount, MaxDays);
//    }
    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = price;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() { return 1; }

    @Override
    public int getMaxDays() { return 7; }
}
