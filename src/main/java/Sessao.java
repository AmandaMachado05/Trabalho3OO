import java.util.ArrayList;
import java.util.List;

public class Sessao {

    private static String nome;
    private List<Livro> livros;

    public Sessao(String nome) {
        this.nome = nome;
        this.livros = new ArrayList<Livro>();
        validarDados();
    }

    private void validarDados() {
        if (nome == null) {
            throw new IllegalArgumentException("Nome Inválido");
        }
    }

    public static String getNome() {
        return nome;
    }

    public void classificaLivro(Livro livro) {
        if (!this.livros.contains(livro)) {
            this.livros.add(livro);
        }
        livro.classificar(this);
    }

    public void trocarsessao(Livro livro, Sessao sessao) {
        this.livros.remove(livro);
        sessao.classificaLivro(livro);
    }

    public boolean verificarLivro(Livro livro) {

        return this.livros.contains(livro);
    }
}

