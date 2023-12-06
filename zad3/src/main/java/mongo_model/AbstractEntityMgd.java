package mongo_model;

import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntityMgd implements Serializable {

    @BsonProperty("_id")
    @BsonId
    public UUID entityId;

    @BsonCreator
    public AbstractEntityMgd(@BsonProperty("_id") UUID entityId) {
        this.entityId = entityId;
    }
}
