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
    void deveRetornarExe????oCodigoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico(null,"Robinson Crusoe","L&PM","1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("C??digo Inv??lido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExe????oNomeNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002",null,"L&PM","1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Nome Inv??lido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExe????oEditoraNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe",null,"1",sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Editora Inv??lida", e.getMessage());
        }
    }

    @Test
    void deveRetornarExe????oNumEdicaoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM",null,sessao,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("N??mero de Edi????o Inv??lido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExe????oSessaoNull() {
        try {
            Sessao sessao = new Sessao("Romances");
            Livro livro = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",null,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Sess??o Inv??lida", e.getMessage());
        }
    }
}