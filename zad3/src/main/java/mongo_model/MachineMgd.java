package mongo_model;

import domain.Machine;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class MachineMgd extends AbstractEntityMgd {

    @BsonProperty("cpus")
    private Integer CPUs;
    @BsonProperty("ram")
    private Integer RAM;
    @BsonProperty("disk")
    private Integer Disk;
    @BsonProperty("system")
    private Machine.SystemType System;
    @BsonProperty("isrented")
    private Boolean isRented = false;

    @BsonCreator
    public MachineMgd(@BsonProperty("_id") UUID entityId,
                      @BsonProperty("cpus") Integer CPUs,
                      @BsonProperty("ram") Integer RAM,
                      @BsonProperty("disk") Integer Disk,
                      @BsonProperty("system") Machine.SystemType System,
                      @BsonProperty("isrented") Boolean isRented) {
        super(entityId);
        this.CPUs = CPUs;
        this.RAM = RAM;
        this.Disk = Disk;
        this.System = System;
        this.isRented = isRented;
    }

    public MachineMgd(@BsonProperty("cpus") Integer CPUs,
                      @BsonProperty("ram") Integer RAM,
                      @BsonProperty("disk") Integer Disk,
                      @BsonProperty("system") Machine.SystemType System,
                      @BsonProperty("isrented") Boolean isRented) {
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
