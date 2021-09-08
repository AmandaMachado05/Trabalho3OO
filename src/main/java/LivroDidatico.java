import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class LivroDidatico implements Livro{

    private String nome;
    private String codigo;
    private String editora;
    private String numEdicao;
    private static Period tempoEmprestimo;
    private static float precoLocacao;
    private static float taxaMultaDia;
    private static float taxaMultaDanoOuPerda;

    private Sessao sessao;
    private List<Autor> autores;

    public LivroDidatico(String codigo, String nome, String editora, String numEdicao,Sessao sessao,Controle controle) {
        this.codigo = codigo;
        this.nome = nome;
        this.editora = editora;
        this.numEdicao = numEdicao;
        this.sessao = sessao;
        this.autores = new ArrayList<Autor>();
        adicionarListaDisponiveis(controle);
        validarDados();
    }

    private void validarDados() {
        if (codigo == null) {
            throw new IllegalArgumentException("Código Inválido");
        }
        if(nome ==null){
            throw new IllegalArgumentException("Nome Inválido");
        }
        if (editora == null) {
            throw new IllegalArgumentException("Editora Inválida");
        }
        if(numEdicao ==null){
            throw new IllegalArgumentException("Número de Edição Inválido");
        }
        if(sessao ==null){
            throw new IllegalArgumentException("Sessão Inválida");
        }
    }

    public String getNome() {
        return nome;
    }

    public Period chamadaTempoEmprestimo(){
        return LivroDidatico.getTempoEmprestimo();
    }
    public float chamadaPrecoLocacao(){
        return LivroDidatico.getPrecoLocacao();
    }
    public float chamadaTaxaMultaDia(){
        return LivroDidatico.getTaxaMultaDia();
    }
    public float chamadaTaxaDanoOuPerda(){
        return LivroDidatico.getTaxaMultaDanoOuPerda();
    }

    public static Period getTempoEmprestimo() {
        return tempoEmprestimo;
    }

    public static void setTempoEmprestimo(int tempoEmprestimo) {
        LivroDidatico.tempoEmprestimo = Period.ofDays(tempoEmprestimo) ;
    }

    public static float getPrecoLocacao() {
        return precoLocacao;
    }

    public static void setPrecoLocacao(float preco) {
        LivroDidatico.precoLocacao = preco;
    }

    public static float getTaxaMultaDia() {
        return taxaMultaDia;
    }

    public static void setTaxaMultaDia(float taxaMultaDia) {
        LivroDidatico.taxaMultaDia = taxaMultaDia;
    }

    public static float getTaxaMultaDanoOuPerda() {
        return taxaMultaDanoOuPerda;
    }

    public static void setTaxaMultaDanoOuPerda(float taxaMultaDanoOuPerda) { LivroDidatico.taxaMultaDanoOuPerda = taxaMultaDanoOuPerda; }

    public void adicionar(Autor autor) {
        if (autor == null) {
            throw new NullPointerException("Autor deve ser informado");
        }
        if (!this.autores.contains(autor)) {
            this.autores.add(autor);
        }
        if (!autor.verificarLivroAdicionado(this)) {
            autor.adicionar(this);
        }
    }

    public void remover(Autor autor) {
        if (autor == null) {
            throw new NullPointerException("Autor deve ser informado");
        }
        if (this.autores.contains(autor)) {
            this.autores.remove(autor);
        }
        if (autor.verificarLivroAdicionado(this)) {
            autor.remover(this);
        }
    }

    public boolean verificarAutorAdicionado(Autor autor) {
        return this.autores.contains(autor);
    }

    public void classificar(Sessao sessao) {
        if (sessao == null) {
            throw new NullPointerException("Sessão vazia");
        }
        if (this.sessao != sessao) {
            Sessao sessaoAnterior = this.sessao;
            this.sessao = sessao;
            sessaoAnterior.trocarsessao(this, sessao);
        }
    }

    public void adicionarListaDisponiveis(Controle controle){
        controle.adicionarListaDisponiveis(this);
    }
}
