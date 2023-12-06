package redis_model;


import domain.Machine;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
public class MachineRds extends AbstractEntityRds {

    @JsonbProperty("cpus")
    private Integer CPUs;
    @JsonbProperty("ram")
    private Integer RAM;
    @JsonbProperty("disk")
    private Integer Disk;
    @JsonbProperty("system")
    private Machine.SystemType System;
    @JsonbProperty("isrented")
    private Boolean isRented = false;

    @JsonbCreator
    public MachineRds(@JsonbProperty("_id") UUID uniqueId,
                      @JsonbProperty("cpus") Integer CPUs,
                      @JsonbProperty("ram") Integer RAM,
                      @JsonbProperty("disk") Integer Disk,
                      @JsonbProperty("system") Machine.SystemType System,
                      @JsonbProperty("isrented") Boolean isRented) {
        super(uniqueId);
        this.CPUs = CPUs;
        this.RAM = RAM;
        this.Disk = Disk;
        this.System = System;
        this.isRented = isRented;
    }

    public MachineRds(@JsonbProperty("cpus") Integer CPUs,
                      @JsonbProperty("ram") Integer RAM,
                      @JsonbProperty("disk") Integer Disk,
                      @JsonbProperty("system") Machine.SystemType System,
                      @JsonbProperty("isrented") Boolean isRented) {
        this.CPUs = CPUs;
        this.RAM = RAM;
        this.Disk = Disk;
        this.System = System;
        this.isRented = isRented;
    }

    public double getBaseCost() throws Exception {
        double price = 1.0;
        if (RAM >= 256 && RAM <= 8192) {
            price += RAM / 10000.0;
        } else if (RAM > 8192 && RAM <= 16384) {
            price += RAM / 7000.0;
        } else if (RAM > 16384 && RAM <= 32768) {
            price += RAM / 5000.0;
        } else if (RAM > 32768 && RAM <= 65536) {
            price += RAM / 3000.0;
        } else {
            throw new Exception("Wrong RAM range given - " + RAM + "! (should be between 256 and 65536)");
        }

        price += (Disk * price) / 1000.0;

        if (CPUs >= 2 && CPUs <= 4) {
            price += (CPUs * price) / 100.0;
        } else if (CPUs > 4 && CPUs <= 8) {
            price += (CPUs * price) / 70.0;
        } else if (CPUs > 8 && CPUs <= 16) {
            price += (CPUs * price) / 50.0;
        } else if (CPUs > 16 && CPUs <= 32) {
            price += (CPUs * price) / 30.0;
        } else {
            throw new Exception("Wrong CPUs range given - " + CPUs + "! (should be between 2 and 32)");
        }

        return price;
    }
}
