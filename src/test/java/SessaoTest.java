import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessaoTest {

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
    void deveRetornarExeçãoNomeNull() {
        try {
            Sessao sessao = new Sessao(null);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Nome Inválido", e.getMessage());
        }
    }

    @Test
    void classificaLivro() {
        Sessao sessao = new Sessao("Programação");
        LivroDidatico livro = new LivroDidatico("000001","Código limpo","Alta Books","1",sessao,controle);
        sessao.classificaLivro(livro);

        assertTrue(sessao.verificarLivro(livro));
        assertEquals("Programação", Sessao.getNome());
    }

    @Test
    void trocarsessao() {
        Sessao sessao = new Sessao("Programação");
        LivroDidatico livro = new LivroDidatico("000001","Código limpo","Alta Books","1",sessao,controle);

        sessao.classificaLivro(livro);

        Sessao sessao2 = new Sessao("Suspense");
        sessao2.classificaLivro(livro);

        assertFalse(sessao.verificarLivro(livro));
        assertTrue(sessao2.verificarLivro(livro));
        assertEquals("Suspense", Sessao.getNome());
    }

}