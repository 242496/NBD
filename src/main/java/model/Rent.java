package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Valid
@Table(name = "Rent")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Rent extends AbstractEntity{

    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private Machine machine;

    @Column
    private Date beginTime;

    @Column
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
        double cost = calculateRentFinalCost();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);
        calendar.add(Calendar.DATE, client.getType().getMaxDays());
        return calendar.getTime();
    }

    public UUID getID() { return ID; }
    public Client getClient() { return client; }
    public Machine getMachine() { return machine; }
    public Date getBeginTime() { return beginTime; }
    public Date getEndTime() { return endTime; }

    public void setID(UUID ID) { this.ID = ID; }
    public void setClient(Client client) { this.client = client; }
    public void setMachine(Machine machine) { this.machine = machine; }
    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
}
