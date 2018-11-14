import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantesTest {
    Restaurantes res = new Restaurantes();
    Restaurante r1, r2;
    List<ModelObject> list;

    @BeforeEach
    void setUp() throws Exception {
        r1 = new Restaurante("nome", "123.123.123", new Endereco(), 5, "3199999999");
        r2 = new Restaurante("nome2", "321.312.312", new Endereco(), 10, "3100000000");
        res.addObject(r1);
        res.addObject(r2);
    }

    @AfterEach
    void finalSetUp() throws Exception {
        res.deleteObject(r1);
        res.deleteObject(r2);
    }

    @Test
    void getAllObjects() {
        try {
            list = res.getAllObjects();
            assertEquals(r1.getNome(), ((Restaurante) list.get(0)).getNome(), "Objeto um não encontrado");
            assertEquals(r2.getNome(), ((Restaurante) list.get(1)).getNome(), "Objeto dois não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getObject() {
        try{
            assertEquals(r1.getNome(), ((Restaurante) res.getObject(r1.getCnpj())).getNome());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void addObject() {
        try{
            Restaurante r3 = new Restaurante("nome3", "123", new Endereco(), 15, "123123");
            res.addObject(r3);
            assertNotNull(res.getObject(r3.getCnpj()));
            res.deleteObject(r3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateObject() {
        try{
            r1.setNome("nome4");
            res.updateObject(r1);
            assertEquals(((Restaurante)res.getObject(r1.getCnpj())).getNome(), "nome4");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteObject() {
        try{
            res.deleteObject(r1);
            assertEquals(null, res.getObject(r1.getCnpj()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}