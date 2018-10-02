import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {
    Cliente cliente;

    @BeforeEach
    void setUp() throws Exception{
        try{
            cliente = new Cliente("nome", "senha123", "email@emaill.com");
        }catch(ExceptionCliente e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAlteraSenha(){
        try{
            cliente.alteraSenha("123456789", "1234");
        }catch (ExceptionCliente e){
            assertEquals("Senha atual invalida", e.getMessage(), "Senha invalida");
        }
    }

}