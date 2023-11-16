package domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
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
public class Intermediate extends ClientType {

    public double applyClientDiscount(double price) {
        ClientDiscount = price * 0.9;
        return ClientDiscount;
    }
    public int getMaxRents() {
        return 2;
    }
    public int getMaxDays() { return 30; }
}
