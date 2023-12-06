package mongo_model;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor

public class UniqueId {
    private UUID id;
    UniqueId(UUID id) {
        this.id = id;
    }
    public UUID getUUID() {
        return id;
    }
}
