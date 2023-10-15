package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import lombok.NoArgsConstructor;
import org.apache.avalon.framework.parameters.ParameterException;

@Entity
@Valid
@Table(name = "Rent")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Rent extends AbstractEntity{

    @Id
    @GeneratedValue
    @Column(name = "Rent_ID")
    private long ID;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @NotNull
    private Client client;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @NotNull
    private Machine machine;

    @Column
    private Date beginTime;

    @Column
    private Date endTime;

    public Rent(Client client, Machine machine) throws OptimisticLockException, ParameterException {
        this.client = client;
        this.machine = machine;
        this.beginTime = new Date();
        this.endTime = calculateEndTime(beginTime);
    }

    public double calculateRentFinalCost() throws ParameterException {
        return this.client.applyDiscount(this.machine.getBaseCost());
    }

    private Date calculateEndTime(Date beginTime) throws ParameterException {
        double cost = calculateRentFinalCost();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);
        calendar.add(Calendar.DATE, client.getType().getMaxDays());
        return calendar.getTime();
    }

    public long getID() { return ID; }
    public Client getClient() { return client; }
    public Machine getMachine() { return machine; }
    public Date getBeginTime() { return beginTime; }
    public Date getEndTime() { return endTime; }

    public void setID(long ID) { this.ID = ID; }
    public void setClient(Client client) { this.client = client; }
    public void setMachine(Machine machine) { this.machine = machine; }
    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
}
