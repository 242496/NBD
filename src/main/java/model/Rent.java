package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.avalon.framework.parameters.ParameterException;

@Entity
@Valid
@Table(name = "Rent")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Rent implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "Rent_ID")
    private long ID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @NotNull
    private Machine machine;

    @Column
    private Date beginTime;
    @Setter
    @Column
    private Date endTime;

    public Rent(Client client, Machine machine) throws ParameterException {
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
}
