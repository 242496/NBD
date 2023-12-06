package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import lombok.Getter;

@Getter
public class AdvancedRds extends ClientTypeRds {

    @JsonbCreator
    public AdvancedRds() { }

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
