package mongo_model;

import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Getter
@BsonDiscriminator(key = "_clazz",value = "advanced")
public class AdvancedMgd extends ClientTypeMgd {

    @BsonCreator
    public AdvancedMgd() { }

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = price * 0.7;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() {
        return 5;
    }

    @Override
    public int getMaxDays() { return 90; }

}
