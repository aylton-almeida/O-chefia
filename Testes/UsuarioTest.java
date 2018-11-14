import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class UsuarioTest {
    Usuario usuario;

    @BeforeEach
    void setUp() throws Exception {
        try {
            usuario = new Usuario("nome", "senha123", "email@emaill.com");
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
}