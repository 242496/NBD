package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
public class ClientRds extends AbstractEntityRds {
    @JsonbProperty("username")
    private String Username;
    @JsonbProperty("type")
    private ClientTypeRds Type;
    @JsonbProperty("activerents")
    private Integer ActiveRents = 0;

    @JsonbCreator
    public ClientRds(@JsonbProperty("_id") UUID entityId,
                     @JsonbProperty("username") String Username,
                     @JsonbProperty("type") ClientTypeRds Type,
                     @JsonbProperty("activerents") Integer ActiveRents) {
        super(entityId);
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }

    public ClientRds(@JsonbProperty("username") String Username,
                     @JsonbProperty("type") ClientTypeRds Type,
                     @JsonbProperty("activerents") Integer ActiveRents) {
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }

    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }
}
