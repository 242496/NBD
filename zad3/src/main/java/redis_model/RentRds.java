package redis_model;

import jakarta.json.bind.annotation.JsonbProperty;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RentRds extends AbstractEntityRds {
    @JsonbProperty("client")
    private ClientRds client;
    @JsonbProperty("machine")
    private MachineRds machine;
    @JsonbProperty("begintime")
    private Date beginTime;
    @JsonbProperty("endtime")
    private Date endTime;

    @BsonCreator
    public RentRds(@BsonProperty("_id") UUID entityId,
                   @BsonProperty("client") ClientRds client,
                   @BsonProperty("machine") MachineRds machine,
                   @BsonProperty("begintime") Date begintime,
                   @BsonProperty("endtime") Date endtime) {
        super(entityId);
        this.client = client;
        this.beginTime = begintime;
        this.machine = machine;
        this.endTime = endtime;
    }

    public RentRds(ClientRds client, MachineRds machine) throws Exception {
        this.client = client;
        this.machine = machine;
        this.beginTime = new Date();
        this.endTime = calculateEndTime(beginTime);
    }

    private Date calculateEndTime(Date beginTime) throws Exception {
        double cost = calculateRentFinalCost();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);
        calendar.add(Calendar.DATE, client.getType().getMaxDays());
        return calendar.getTime();
    }

    public double calculateRentFinalCost() throws Exception {
        return this.client.applyDiscount(this.machine.getBaseCost());
    }
}
