package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import lombok.Getter;

@Getter
public class IntermediateRds extends ClientTypeRds {

    @JsonbCreator
    public IntermediateRds() { }

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
