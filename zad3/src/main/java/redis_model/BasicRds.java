package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import lombok.Getter;

@Getter
public class BasicRds extends ClientTypeRds {

    @JsonbCreator
    public BasicRds() { }

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
