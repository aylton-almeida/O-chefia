import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {
    private Restaurante restaurante;

    @BeforeEach
    void setUp() throws Exception{
        restaurante = new Restaurante("Nome", "123123", new Endereco(), 20, "213123");
        restaurante.addPratoCardapio(new Prato("prato1", 15, "Ingredientes", 1));
        restaurante.addPratoCardapio(new Prato("prato2", 20, "Ingredientes2", 2));
        restaurante.addPratoCardapio(new Prato("prato3", 20, "Ingredientes3", 1));
        restaurante.addPratoCardapio(new Prato("prato4", 20, "Ingredientes4", 3));
    }

    @Test
    void getPratosPrincipais() {
        assertEquals("prato1", restaurante.getPratosPrincipais().get(0).getNome());
        assertEquals("prato3", restaurante.getPratosPrincipais().get(1).getNome());
    }
}