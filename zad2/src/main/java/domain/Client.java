package domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import model.ClientTypeMgd;


@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Client extends AbstractEntity{
    private String Username;
    private ClientTypeMgd Type;
    private int ActiveRents;

    public Client(String Username, ClientTypeMgd Type) {
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = 0;
    }


    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }

    public ClientTypeMgd getType() { return Type; }

    public int getActiveRents() { return ActiveRents; }

}
