import java.util.ArrayList;
import java.util.List;

public class Autor {

    private String codigo;
    private String nome;
    private List<Livro> livros;

    public Autor(String codigo,String nome) {
        this.codigo = codigo;
        this.nome = nome;
        this.livros = new ArrayList<Livro>();
        validarDados();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private void validarDados() {
        if (codigo == null) {
            throw new IllegalArgumentException("Código Inválido");
        }
        if (nome == null) {
            throw new IllegalArgumentException("Nome Inválido");
        }
    }

    public void adicionar(Livro livro) {
        if (livro == null) {
            throw new NullPointerException("Livro deve ser informado");
        }
        if (!this.livros.contains(livro)) {
            this.livros.add(livro);
        }
        if (!livro.verificarAutorAdicionado(this)) {
            livro.adicionar(this);
        }
    }

    public void remover(Livro livro) {
        if (livro == null) {
            throw new NullPointerException("Livro deve ser informado");
        }
        if (this.livros.contains(livro)) {
            this.livros.remove(livro);
        }
        if (livro.verificarAutorAdicionado(this)) {
            livro.remover(this);
        }
    }


    public boolean verificarLivroAdicionado(Livro livro) { return this.livros.contains(livro); }
}
