package redis_model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntityRds implements Serializable {

    @JsonbProperty("_id")
    protected UUID entityId;

    @JsonbCreator
    public AbstractEntityRds(@JsonbProperty("_id") UUID entityId) {
        this.entityId = entityId;
    }
}
