package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.Valid;

@Entity
@Valid
@DiscriminatorColumn(name = "Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
public abstract class ClientType extends AbstractEntity{
//    @Id
//    @GeneratedValue
//    @Column(name = "Type_ID")
//    protected long ID;
    @Column
    protected int MaxRents;
    @Column
    protected double ClientDiscount;
    @Column
    protected int MaxDays;

    public ClientType() {
        this.MaxRents = getMaxRents();
        this.ClientDiscount = getClientDiscount();
        this.MaxDays = getMaxDays();
    }

    public int getMaxRents() {
        return MaxRents;
    }

    public double getClientDiscount() {
        return ClientDiscount;
    }


    public void setClientDiscount(double clientDiscount) {
        ClientDiscount = clientDiscount;
    }

    public double applyClientDiscount(double price) {
        return price;
    }

    public int getMaxDays() {return MaxDays; }
}
