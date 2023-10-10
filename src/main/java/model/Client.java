package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.io.Serializable;
import lombok.NoArgsConstructor;


@Entity
@Valid
@Table(name = "Client")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Client_ID")
    private long ID;
    @Column
    private String Username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name= "Type_id")
    private ClientType Type;

    public Client(String Username, ClientType Type) {
        this.Username = Username;
        this.Type = Type;
    }

    public int getClientRents() {
        return this.Type.getMaxRents();
    }

    public double applyDiscount(double price) {
        return this.Type.applyClientDiscount(price);
    }

    public ClientType getType() { return Type; }

    public void setType(ClientType type) { Type = type; }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
