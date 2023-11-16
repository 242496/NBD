import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Admin;
import domain.Advanced;
import domain.Basic;
import domain.Client;
import domain.ClientType;
import domain.Intermediate;
import mapper.ClientMapper;
import mapper.ClientTypeMapper;
import model.AdminMgd;
import model.AdvancedMgd;
import model.BasicMgd;
import model.ClientTypeMgd;
import model.IntermediateMgd;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ClientTypeMapperTest {
    private static ClientTypeMapper clientTypeMapper;

    @BeforeAll
    public static void beforeAll(){
        clientTypeMapper = new ClientTypeMapper();
    }

    @Test
    public void toDomainModel() {
        ClientTypeMgd adminMgd = new AdminMgd();
        ClientType admin = clientTypeMapper.toDomainModel(adminMgd);
        assertEquals(Admin.class, admin.getClass());

        ClientTypeMgd advancedMgd = new AdvancedMgd();
        ClientType advanced = clientTypeMapper.toDomainModel(advancedMgd);
        assertEquals(Advanced.class, advanced.getClass());

        ClientTypeMgd intermediateMgd = new IntermediateMgd();
        ClientType intermediate = clientTypeMapper.toDomainModel(intermediateMgd);
        assertEquals(Intermediate.class, intermediate.getClass());

        ClientTypeMgd basicMgd = new BasicMgd();
        ClientType basic = clientTypeMapper.toDomainModel(basicMgd);
        assertEquals(Basic.class, basic.getClass());
    }

    @Test
    public void toMongoDocument() {
        ClientType admin = new Admin();
        ClientTypeMgd adminMgd = clientTypeMapper.toMongoDocument(admin);
        assertEquals(AdminMgd.class, adminMgd.getClass());

        ClientType advanced = new Advanced();
        ClientTypeMgd advancedMgd = clientTypeMapper.toMongoDocument(advanced);
        assertEquals(AdvancedMgd.class, advancedMgd.getClass());

        ClientType intermediate = new Intermediate();
        ClientTypeMgd intermediateMgd = clientTypeMapper.toMongoDocument(intermediate);
        assertEquals(IntermediateMgd.class, intermediateMgd.getClass());

        ClientType basic = new Basic();
        ClientTypeMgd basicMgd = clientTypeMapper.toMongoDocument(basic);
        assertEquals(BasicMgd.class, basicMgd.getClass());
    }
}
