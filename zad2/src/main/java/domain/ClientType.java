package domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
public abstract class ClientType extends AbstractEntity{
    protected int MaxRents;
    protected double ClientDiscount;
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
