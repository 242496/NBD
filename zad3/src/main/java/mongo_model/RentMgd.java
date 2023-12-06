package mongo_model;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RentMgd extends AbstractEntityMgd {
    @BsonProperty("client")
    private ClientMgd client;

    @BsonProperty("machine")
    private MachineMgd machine;
    @BsonProperty("begintime")
    private Date beginTime;
    @BsonProperty("endtime")
    private Date endTime;

    @BsonCreator
    public RentMgd(@BsonProperty("_id") UUID entityId,
                   @BsonProperty("client") ClientMgd client,
                   @BsonProperty("machine") MachineMgd machine,
                   @BsonProperty("begintime") Date begintime,
                   @BsonProperty("endtime") Date endtime) {
        super(entityId);
        this.client = client;
        this.beginTime = begintime;
        this.machine = machine;
        this.endTime = endtime;
    }

    public RentMgd(ClientMgd client, MachineMgd machine) throws Exception {
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
