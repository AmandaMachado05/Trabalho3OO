import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorTest {

    Controle controle;

    @BeforeEach
    void setUp() {
        controle = new Controle();
    }

    @Test
    void deveRetornarExeçãoCodigoNull() {
        try {
            Autor autor = new Autor(null, "Robert C. Martin");
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Código Inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoNomeNull() {
        try {
            Autor autor = new Autor("000001", null);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Nome Inválido", e.getMessage());
        }
    }

    @Test
    void deveAdicionarLivro() {
        Sessao sessao = new Sessao("Programação");
        LivroDidatico livro = new LivroDidatico("000001","Código limpo","Alta Books","1",sessao,controle);
        Autor autor = new Autor("000001", "Robert C. Martin");

        autor.adicionar(livro);

        assertTrue(livro.verificarAutorAdicionado(autor));
        assertTrue(autor.verificarLivroAdicionado(livro));
    }

    @Test
    void deveRemoverLivro() {
        Sessao sessao = new Sessao("Programação");
        LivroDidatico livro = new LivroDidatico("000001","Código limpo","Alta Books","1",sessao,controle);
        Autor autor = new Autor("000001", "Robert C. Martin");

        autor.adicionar(livro);
        autor.remover(livro);

        assertFalse(livro.verificarAutorAdicionado(autor));
        assertFalse(autor.verificarLivroAdicionado(livro));

    }

    @Test
    void deveRetornarExcecaoAdicionarLivroNulo() {
        try {
            Autor autor = new Autor("000001", "Robert C. Martin");
            autor.adicionar(null);
            fail();
        }
        catch (NullPointerException e) {
            assertEquals("Livro deve ser informado", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoRemoverLivroNulo() {
        try {
            Autor autor = new Autor("000001", "Robert C. Martin");
            autor.remover(null);
            fail();
        }
        catch (NullPointerException e) {
            assertEquals("Livro deve ser informado", e.getMessage());
        }
    }

}