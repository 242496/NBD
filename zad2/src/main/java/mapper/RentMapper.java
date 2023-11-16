package mapper;

import domain.Rent;
import java.util.HashMap;
import java.util.Map;
import model.ClientMgd;
import model.MachineMgd;
import model.RentMgd;

public class RentMapper {
    private ClientMapper clientMapper;
    private MachineMapper machineMapper;

    public RentMapper() {
        clientMapper = new ClientMapper();
        machineMapper = new MachineMapper();
    }

    public RentMgd toMongoDocument(Rent rent) {
        RentMgd rentMgd = new RentMgd();
        rentMgd.setClient(clientMapper.toMongoDocument(rent.getClient()));
        rentMgd.setMachine(machineMapper.toMongoDocument(rent.getMachine()));
        rentMgd.setEntityId(rent.getEntityId());
        rentMgd.setBeginTime(rent.getBeginTime());
        rentMgd.setEndTime(rent.getEndTime());
        return rentMgd;
    }

    public Rent toDomainModel(RentMgd rentMgd) throws Exception {
        Rent rent = new Rent(
                clientMapper.toDomainModel(rentMgd.getClient()),
                machineMapper.toDomainModel(rentMgd.getMachine())
        );
        rent.setBeginTime(rentMgd.getBeginTime());
        rent.setEndTime(rentMgd.getEndTime());
        return rent;
    }

//    public static OrderMgd toMongoDocument(Order order){
//        Map<String, ItemMgd> orderMgdItems = new HashMap<>();
//        order.getOrderItems().keySet().forEach( key ->{
//                    orderMgdItems.put(key, ItemMapper.toMongoDocument(order.getOrderItems().get(key)));
//                }
//        );
//        return OrderMgd.builder()
//                .uniqueId(order.getUniqueId())
//                .client(ClientMapper.toMongoDocument(order
//                        .getClient()))
//                .shippingAddress(AddressMapper.toMongoDocument(order.getShippingAddress()))
//                .orderItems(orderMgdItems)
//                .orderValue(order.getOrderValue())
//                .createdOn(order.getCreatedOn())
//                .isPaid(order.isPaid())
//                .isDelivered(order.isDelivered())
//                .build();
//    }
//
//    public static Order toDomainModel(OrderMgd order){
//        Map<String, Item> orderItems = new HashMap<>();
//        order.getOrderItems().keySet().forEach( key ->{
//                    orderItems.put(key, ItemMapper.toDomainModel(order.getOrderItems().get(key)));
//                }
//        );
//        return Order.builder()
//                .uniqueId(order.getUniqueId())
//                .client(ClientMapper.toDomainModel(order.getClient()))
//                .shippingAddress(AddressMapper.toDomainModel(order.getShippingAddress()))
//                .orderItems(orderItems)
//                .orderValue(order.getOrderValue())
//                .createdOn(order.getCreatedOn())
//                .isPaid(order.isPaid())
//                .isDelivered(order.isDelivered())
//                .build();
//    }
}
