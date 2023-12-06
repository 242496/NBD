package domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import mongo_model.ClientTypeMgd;
import redis_model.ClientTypeRds;


@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Client extends AbstractEntity{
    private String Username;
    private ClientTypeMgd TypeMgd;
    private ClientTypeRds TypeRds;

    private ClientType Type;
    private int ActiveRents;

    public Client(String Username, ClientTypeMgd TypeMgd) {
        this.Username = Username;
        this.TypeMgd = TypeMgd;
        this.ActiveRents = 0;
    }

    public Client(String Username, ClientTypeRds TypeRds) {
        this.Username = Username;
        this.TypeRds = TypeRds;
        this.ActiveRents = 0;
    }

    public Client(String Username, ClientType Type) {
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = 0;
    }


    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }

    public ClientType getType() { return Type; }
    public ClientTypeMgd getTypeMgd() { return TypeMgd; }
    public ClientTypeRds getTypeRds() { return TypeRds; }

    public void setType(ClientType Type) {
        this.Type = Type;
    }
    public void setTypeMgd(ClientTypeMgd Type) {
        this.TypeMgd = Type;
    }
    public void setTypeRds(ClientTypeRds Type) {
        this.TypeRds = Type;
    }

    public int getActiveRents() { return ActiveRents; }

}
