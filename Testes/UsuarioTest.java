import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {
    Usuario usuario;

    @BeforeEach
    void setUp() throws Exception{
        try{
            usuario = new Usuario("nome", "senha123", "email@emaill.com");
        }catch(ExceptionUsuario e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAlteraSenha(){
        try{
            usuario.alteraSenha("123456789", "1234");
        }catch (ExceptionUsuario e){
            assertEquals("Senha atual invalida", e.getMessage(), "Senha invalida");
        }
    }

}