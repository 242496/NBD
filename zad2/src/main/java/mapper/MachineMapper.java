package mapper;


import domain.Machine;
import model.MachineMgd;

public class MachineMapper {
    public MachineMgd toMongoDocument(Machine machine) {
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
    public Machine toDomainModel(MachineMgd machineMgd) {
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
//    public static MachineMgd toMongoDocument(Machine machine) {
//        return MachineMgd.builder()
//                .entityId(machine.getEntityId())
//                .CPUs(machine.getCPUs())
//                .RAM(machine.getRAM())
//                .Disk(machine.getDisk())
//                .isRented(machine.isRented())
//                .build();
//    }
//
//    public static Machine toDomainModel(MachineMgd machine){
//        return Machine.builder()
//                .entityId(machine.getEntityId())
//                .CPUs(machine.getCPUs())
//                .RAM(machine.getRAM())
//                .Disk(machine.getDisk())
//                .isRented(machine.getIsRented())
//                .build();
//    }
}
