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
public class Advanced extends ClientType{
    public double applyClientDiscount(double price) {
        ClientDiscount = price * 0.7;
        return ClientDiscount;
    }
    public int getMaxRents() {
        return 5;
    }
    public int getMaxDays() { return 90; }
}
