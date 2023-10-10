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
@DiscriminatorValue("Intermediate")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Intermediate extends ClientType{

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = price * 0.9;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() {
        return 2;
    }

    @Override
    public int getMaxDays() { return 30; }
}
