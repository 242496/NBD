package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import lombok.Getter;

@Getter
public class AdminRds extends ClientTypeRds {

    @JsonbCreator
    public AdminRds() { }

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
