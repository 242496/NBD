package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.NoArgsConstructor;


@Entity
@Valid
@Table(name = "Client")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Client extends AbstractEntity{
//    @Id
//    @GeneratedValue
//    @Column(name = "Client_ID")
//    private long ID;

    @Column
    private String Username;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn (name= "Type_id")
    private ClientType Type;

    @Column
    private int ActiveRents;

    public Client(String Username, ClientType Type) {
        this.Username = Username;
        this.Type = Type;
        this.ActiveRents = getActiveRents();
    }


    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }

    public ClientType getType() { return Type; }

    public void setType(ClientType type) { Type = type; }

    public UUID getID() {
        return ID;
    }

    public int getActiveRents() { return ActiveRents; }

    public void setActiveRents(int activeRents) { ActiveRents = activeRents; }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
