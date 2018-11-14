import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuariosTest {

    Usuarios usuarios = new Usuarios();
    Usuario usuario, usuario2;
    List<ModelObject> list;

    @BeforeEach
    void setUp() throws Exception {
        usuario = new Usuario("nome", "senha123", "email@emaill.com");
        usuario2 = new Usuario("user", "password", "e@e.com");
        usuarios.addObject(usuario);
        usuarios.addObject(usuario2);
    }

    @Test
    void getAllObjects() {
        try {
            list = usuarios.getAllObjects();
            assertEquals(usuario.getNome(), ((Usuario) list.get(0)).getNome(), "Usuario 1 não encontrado");
            assertEquals(usuario2.getNome(), ((Usuario) list.get(1)).getNome(), "Usuario 2 não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getObject() {
        try {
            assertEquals(usuario2.getNome(), ((Usuario) usuarios.getObject("e@e.com")).getNome());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void addObject() {
        try {
            Usuario user = new Usuario("jorge", "senhasenha", "a@a.com");
            usuarios.addObject(user);
            assertNotNull(usuarios.getObject(user.getEmail()));
            usuarios.deleteObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateObject() {
        try {
            usuarios.updateObject(new Usuario("aylton", "senha123", "email@emaill.com"));
            assertEquals(((Usuario) usuarios.getObject("email@emaill.com")).getNome(), "aylton");
            usuarios.updateObject(new Usuario("nome", "senha123", "email@emaill.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteObject() {
        try {
            Usuario user = new Usuario("jorge", "senhasenha", "a@a.com");
            usuarios.addObject(user);
            assertNotNull(usuarios.getObject(user.getEmail()));
            usuarios.deleteObject(user);
            assertEquals(null, usuarios.getObject(user.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}