package model;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Getter
@BsonDiscriminator(key = "_clazz",value = "intermediate")
public class IntermediateMgd extends ClientTypeMgd {
    @BsonCreator
    public IntermediateMgd() { }

//    @JsonCreator
//    public Intermediate(@JsonProperty("_id") UniqueId entityId,
//                        @JsonProperty("maxrents") int MaxRents,
//                        @JsonProperty("clientdiscount") double ClientDiscount,
//                        @JsonProperty("maxdays") int MaxDays) {
//        super(entityId, MaxRents, ClientDiscount, MaxDays);
//    }

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = price * 0.9;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() {
        return 2;
    }

    @Override
    public int getMaxDays() { return 30; }
}
