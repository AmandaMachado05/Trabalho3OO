import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroNaoDidaticoTest {

    Controle controle;

    @BeforeEach
    void setUp() {
        controle = new Controle();
        LivroDidatico.setTempoEmprestimo(7);
        LivroDidatico.setPrecoLocacao(10.0f);
        LivroDidatico.setTaxaMultaDia(1.0f);
        LivroDidatico.setTaxaMultaDanoOuPerda(25.0f);
    }

    @Test
    void deveAdicionarAutor() {
        Sessao sessao = new Sessao("Romances");
        Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
        Autor autor = new Autor("000002", "Daniel Defoe");
        livro.adicionar(autor);

        assertTrue(livro.verificarAutorAdicionado(autor));
        assertTrue(autor.verificarLivroAdicionado(livro));
    }

    @Test
    void deveRemoverAutor() {
        Sessao sessao = new Sessao("Romances");
        Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
        Autor autor = new Autor("000002", "Daniel Defoe");

        livro.adicionar(autor);
        livro.remover(autor);

        assertFalse(livro.verificarAutorAdicionado(autor));
        assertFalse(autor.verificarLivroAdicionado(livro));
    }

    @Test
    void deveRetornarExcecaoAdicionarAutorNulo() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
            livro.adicionar(null);
            fail();
        }
        catch (NullPointerException e) {
            assertEquals("Autor deve ser informado", e.getMessage());
        }
    }

    @Test
    void deveRetornarExcecaoRemoverAutorNulo() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
            livro.remover(null);
            fail();
        }
        catch (NullPointerException e) {
            assertEquals("Autor deve ser informado", e.getMessage());
        }
    }

    @Test
    void classificaLivro() {
        Sessao sessao = new Sessao("Romances");
        Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
        sessao.classificaLivro(livro);

        assertTrue(sessao.verificarLivro(livro));
        assertEquals("Romances", Sessao.getNome());
    }

    @Test
    void trocarsessao() {
        Sessao sessao = new Sessao("Romances");
        Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao,controle);
        sessao.classificaLivro(livro);

        Sessao sessao2 = new Sessao("Aventura");
        sessao2.classificaLivro(livro);

        assertFalse(sessao.verificarLivro(livro));
        assertTrue(sessao2.verificarLivro(livro));
        assertEquals("Aventura", Sessao.getNome());
    }

    @Test
    void deveRetornarExeçãoCodigoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico(null,"Robinson Crusoe","L&PM","1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Código Inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoNomeNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002",null,"L&PM","1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Nome Inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoEditoraNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe",null,"1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Editora Inválida", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoNumEdicaoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM",null,sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Número de Edição Inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoSessaoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",null,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Sessão Inválida", e.getMessage());
        }
    }
}