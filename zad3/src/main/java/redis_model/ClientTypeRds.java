package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "admin", type = AdminRds.class),
        @JsonbSubtype(alias = "advanced", type = AdvancedRds.class),
        @JsonbSubtype(alias = "intermediate", type = IntermediateRds.class),
        @JsonbSubtype(alias = "basic", type = BasicRds.class)
})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
public abstract class ClientTypeRds extends AbstractEntityRds {
    @JsonbProperty("maxrents")
    protected int MaxRents;
    @JsonbProperty("clientdiscount")
    protected double ClientDiscount;
    @JsonbProperty("maxdays")
    protected int MaxDays;

    @JsonbCreator
    public ClientTypeRds(@JsonbProperty("_id") UUID entityId,
                         @JsonbProperty("maxrents") int MaxRents,
                         @JsonbProperty("clientdiscount") double ClientDiscount,
                         @JsonbProperty("maxdays") int MaxDays) {
        super(entityId);
        this.MaxRents = MaxRents;
        this.ClientDiscount = ClientDiscount;
        this.MaxDays = MaxDays;
    }

    public ClientTypeRds() {
        this.MaxRents = getMaxRents();
        this.ClientDiscount = getClientDiscount();
        this.MaxDays = getMaxDays();
    }

    public double applyClientDiscount(double price) {
        return price;
    }
}
