package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@DiscriminatorValue("Basic")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Basic extends ClientType{

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = price;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() { return 1; }

    @Override
    public int getMaxDays() { return 7; }
}
