package mapper;


import domain.Machine;
import java.util.UUID;
import mongo_model.MachineMgd;
import redis_model.MachineRds;

public class MachineMapper {
    public static MachineMgd toMongoDocument(Machine machine) {
        if(machine.getClass() == Machine.class) {
            MachineMgd machineMgd = new MachineMgd(
                    machine.getEntityId(),
                    machine.getCPUs(),
                    machine.getRAM(),
                    machine.getDisk(),
                    machine.getSystem(),
                    machine.isRented()
            );
            return machineMgd;
        }
        return null;
    }

    public static MachineRds toRedisDocument(Machine machine) {
        if (machine.getEntityId() == null) {
            machine.setEntityId(UUID.randomUUID());
        }
        if(machine.getClass() == Machine.class) {
            MachineRds machineRds = new MachineRds(
                    machine.getEntityId(),
                    machine.getCPUs(),
                    machine.getRAM(),
                    machine.getDisk(),
                    machine.getSystem(),
                    machine.isRented()
            );
            return machineRds;
        }
        return null;
    }
    public static Machine toDomainModel(MachineMgd machineMgd) {
        if(machineMgd.getClass() == MachineMgd.class) {
            Machine machine = new Machine(
                    machineMgd.getCPUs(),
                    machineMgd.getRAM(),
                    machineMgd.getDisk(),
                    machineMgd.getSystem()
            );
            return machine;
        }
        return null;
    }

    public static Machine toDomainModel(MachineRds machineRds) {
        if(machineRds.getClass() == MachineRds.class) {
            Machine machine = new Machine(
                    machineRds.getCPUs(),
                    machineRds.getRAM(),
                    machineRds.getDisk(),
                    machineRds.getSystem()
            );
            return machine;
        }
        return null;
    }
}
