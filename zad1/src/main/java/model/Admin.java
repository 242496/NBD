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
@DiscriminatorValue("Admin")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Admin extends ClientType{

    @Override
    public double applyClientDiscount(double price) {
        ClientDiscount = 0.0;
        return ClientDiscount;
    }

    @Override
    public int getMaxRents() {
        return 1000;
    }

    @Override
    public int getMaxDays() { return 365; }
}
