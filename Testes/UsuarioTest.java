import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class UsuarioTest {
    Usuario usuario, usuario2;
    List<ModelObject> list;

    @BeforeEach
    void setUp() throws Exception {
        try {
            usuario = new Usuario("nome", "senha123", "email@emaill.com");
            usuario2 = new Usuario("user", "password", "e@e.com");
            usuario.addObject(usuario);
            usuario.addObject(usuario2);
        } catch (ExceptionUsuario e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testAlteraSenha() {
        try {
            usuario.alteraSenha("123456789", "1234");
        } catch (ExceptionUsuario e) {
            assertEquals("Senha atual invalida", e.getMessage(), "Senha invalida");
        }
    }

    @Test
    void getAllObjects() {
        try {
            list = usuario.getAllObjects();
            assertEquals(usuario.getNome(), ((Usuario)list.get(0)).getNome(), "Usuario 1 não encontrado");
            assertEquals(usuario2.getNome(), ((Usuario)list.get(1)).getNome(), "Usuario 2 não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getObject() {
        try{
            assertEquals(usuario2.getNome(), ((Usuario)usuario.getObject("e@e.com")).getNome());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void addObject() {
        try{
            Usuario user = new Usuario("jorge", "senhasenha", "a@a.com");
            user.addObject(user);
            assertNotNull(user.getObject(user.getEmail()));
            user.deleteObject(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void updateObject() {
        try{
            usuario.updateObject(new Usuario("aylton", "senha123", "email@emaill.com"));
            assertEquals(((Usuario)usuario.getObject("email@emaill.com")).getNome(), "aylton");
            usuario.updateObject(new Usuario("nome", "senha123", "email@emaill.com"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void deleteObject() {
        try{
            Usuario user = new Usuario("jorge", "senhasenha", "a@a.com");
            user.addObject(user);
            assertNotNull(user.getObject(user.getEmail()));
            user.deleteObject(user);
            assertEquals(null, user.getObject(user.getEmail()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}