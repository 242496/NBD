package domain;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Rent extends AbstractEntity {

    private Client client;
    private Machine machine;
    private Date beginTime;
    private Date endTime;

    public Rent(Client client, Machine machine) throws Exception {
        this.client = client;
        this.machine = machine;
        this.beginTime = new Date();
        this.endTime = calculateEndTime(beginTime);
    }

    public double calculateRentFinalCost() throws Exception {
        return this.client.applyDiscount(this.machine.getBaseCost());
    }

    private Date calculateEndTime(Date beginTime) throws Exception {
        double cost = 1.0;//calculateRentFinalCost();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);
//        calendar.add(Calendar.DATE, client.getType().getMaxDays());
        return calendar.getTime();
    }

    public UUID getEntityId() { return entityId; }
    public Client getClient() { return client; }
    public Machine getMachine() { return machine; }
    public Date getBeginTime() { return beginTime; }
    public Date getEndTime() { return endTime; }

    public void setEntityId(UUID entityId) { this.entityId = entityId; }
    public void setClient(Client client) { this.client = client; }
    public void setMachine(Machine machine) { this.machine = machine; }
    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
}
