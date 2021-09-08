
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LocacaoTest {

    Controle controle;
    LivroDidatico livro;
    LivroNaoDidatico livro2;
    Cliente cliente;
    Sessao sessao,sessao2;

    @BeforeEach
    void setUp() {
        controle = new Controle();
        sessao = new Sessao("Programação");
        sessao2 = new Sessao("Romances");
        livro = new LivroDidatico("000001","Código limpo","Alta Books","1",sessao,controle);
        livro2 = new LivroNaoDidatico("000002","Robinson Crusoe","L&PM","1",sessao2,controle);
        LivroDidatico.setTempoEmprestimo(7);
        LivroDidatico.setPrecoLocacao(10.0f);
        LivroDidatico.setTaxaMultaDia(1.0f);
        LivroDidatico.setTaxaMultaDanoOuPerda(25.0f);
        LivroNaoDidatico.setTempoEmprestimo(6);
        LivroNaoDidatico.setPrecoLocacao(80.0f);
        LivroNaoDidatico.setTaxaMultaDia(1.5f);
        LivroNaoDidatico.setTaxaMultaDanoOuPerda(200.0f);
        cliente= new Cliente(000001,"Amanda","123.456.789-10","91234-5678","Av.Rio Branco",123,"apto 301","Centro","Juiz de Fora","MG","36046-146");
    }

    @Test
    void deveLocarLivros() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);
        livrosDesejados.add(livro2);

        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);

        List<Livro> livrosValidados = Arrays.asList(livro,livro2);
        List<LocalDate> datasEstimadas = Arrays.asList(locacao.formatarDatas("15/09/2021"),locacao.formatarDatas("08/03/2022"));

        assertEquals(livrosValidados,controle.validarLivrosDesejados(livrosDesejados));
        assertEquals(datasEstimadas,locacao.getListaDataDevolucaoEstimada());
        assertEquals(90.0f,locacao.calcularPrecoLocacaoTotal());
    }

    @Test
    void deveRetornarExeçãoClienteNull() {
        try {
            List<Livro> livrosDesejados=new ArrayList<Livro>();
            livrosDesejados.add(livro);
            livrosDesejados.add(livro2);
            Locacao locacao=new Locacao(null,"08/09/2021",livrosDesejados,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Cliente Inválido", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoDataLocacaoNull() {
        try {
            List<Livro> livrosDesejados=new ArrayList<Livro>();
            livrosDesejados.add(livro);
            livrosDesejados.add(livro2);
            Locacao locacao=new Locacao(cliente,null,livrosDesejados,controle);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Data de locação vazia", e.getMessage());
        }
    }

    @Test
    void deveRetornarExeçãoListaDesejosVazia() {
        try{
        List<Livro> livrosDesejados = new ArrayList<Livro>();
        Locacao locacao = new Locacao(cliente, "08/09/2021", livrosDesejados, controle);
        fail();
        } catch (NullPointerException e) {
            assertEquals("Lista de livros desejados vazia", e.getMessage());
        }

    }

    @Test
    void deveRetornarExeçãoDevolucaoCerta() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);
        livrosDesejados.add(livro2);

        List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();

        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);
        Locacao.Devolucao devolucao= locacao.new Devolucao("15/09/2021",0,livrosDanificadosOuPerdidos);
        devolucao.removerListaIndisponiveis(controle);

        assertEquals(0.0f, devolucao.calcularMulta());
    }

    @Test
    void deveRetornarExeçãoDevolucaoMultaAtraso() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);
        livrosDesejados.add(livro2);
        List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();
        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);
        Locacao.Devolucao devolucao= locacao.new Devolucao("15/09/2021",5,livrosDanificadosOuPerdidos);
        devolucao.removerListaIndisponiveis(controle);

        assertEquals(12.5f, devolucao.calcularMulta());
    }

    @Test
    void deveRetornarExeçãoDevolucaoMultaAtrasoEDanoOuPerda() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);
        livrosDesejados.add(livro2);
        List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();
        livrosDanificadosOuPerdidos.add(livro);
        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);
        Locacao.Devolucao devolucao= locacao.new Devolucao("15/09/2021",5,livrosDanificadosOuPerdidos);
        devolucao.removerListaIndisponiveis(controle);

        assertEquals(32.5f, devolucao.calcularMulta());
    }

    @Test
    void deveAlterarListaDisponiveis() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);

        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);

        List<Livro> livrosDisponiveis = Arrays.asList(livro2);
        List<Livro> livrosIndisponiveis = Arrays.asList(livro);


        assertEquals(livrosDisponiveis,controle.exibirListaDisponiveis());
        assertEquals(livrosIndisponiveis,controle.exibirListaIndisponiveis());
    }

    @Test
    void deveAlterarListaDisponiveisAposDevolucao() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);

        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);

        List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();
        Locacao.Devolucao devolucao= locacao.new Devolucao("15/09/2021",5,livrosDanificadosOuPerdidos);
        devolucao.removerListaIndisponiveis(controle);

        List<Livro> livrosDisponiveis = Arrays.asList(livro2,livro);
        List<Livro> livrosIndisponiveis = Arrays.asList();


        assertEquals(livrosDisponiveis,controle.exibirListaDisponiveis());
        assertEquals(livrosIndisponiveis,controle.exibirListaIndisponiveis());
    }

    @Test
    void deveAlterarListaDisponiveisAposDevolucaoLivroDanificadoOuPerdido() {
        List<Livro> livrosDesejados=new ArrayList<Livro>();
        livrosDesejados.add(livro);
        livrosDesejados.add(livro2);

        Locacao locacao=new Locacao(cliente,"08/09/2021",livrosDesejados,controle);
        locacao.removerListaDisponiveis(controle);

        List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();
        livrosDanificadosOuPerdidos.add(livro);
        Locacao.Devolucao devolucao= locacao.new Devolucao("15/09/2021",5,livrosDanificadosOuPerdidos);
        devolucao.removerListaIndisponiveis(controle);

        List<Livro> livrosDisponiveis = Arrays.asList(livro2);
        List<Livro> livrosIndisponiveis = Arrays.asList(livro);


        assertEquals(livrosDisponiveis,controle.exibirListaDisponiveis());
        assertEquals(livrosIndisponiveis,controle.exibirListaIndisponiveis());
    }

}



