package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import java.io.Serializable;
import lombok.NoArgsConstructor;
import org.apache.avalon.framework.parameters.ParameterException;

@Entity
@Valid
@Table(name = "Machine")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class Machine implements Serializable {

    public enum SystemType {
        DEBIAN, WINDOWS10, WINDOWS7, FEDORA;
    }
    @Id
    @GeneratedValue
    @Column(name = "Machine_ID")
    private long ID;

    @Column
    private int CPUs;
    @Column
    private int RAM;
    @Column
    private int Disk;

    @Column
    private SystemType System;

    public Machine(int CPUs, int RAM, int Disk, SystemType System) {
        this.CPUs = CPUs;
        this.RAM = RAM;
        this.Disk = Disk;
        this.System = System;
    }

    public double getBaseCost() throws ParameterException {
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
            throw new ParameterException("Wrong RAM range given - " + RAM + "! (should be between 256 and 65536)");
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
            throw new ParameterException("Wrong CPUs range given - " + CPUs + "! (should be between 2 and 32)");
        }

        if (System == SystemType.WINDOWS7) {
            price += (5 * price) / 100.0;
        } else if (System == SystemType.WINDOWS10) {
            price += (10 * price) / 100.0;
        } else if (System != SystemType.FEDORA && System != SystemType.DEBIAN) {
            throw new ParameterException("Wrong system name - " + getSystem() + " (options: FEDORA, DEBIAN, WINDOWS7, WINDOWS10)");
        }

        return price;
    }

    public long getID() {
        return ID;
    }

    public int getCPUs() {
        return CPUs;
    }

    public int getRAM() {
        return RAM;
    }

    public int getDisk() {
        return Disk;
    }

    public SystemType getSystem() { return System; }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setCPUs(int CPUs) {
        this.CPUs = CPUs;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public void setDisk(int disk) {
        Disk = disk;
    }

    public void setSystem(SystemType system) { System = system; }
}
