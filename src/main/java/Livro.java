import java.time.LocalDate;
import java.time.Period;

//Amanda Lopes Machado, 201935002
//João Pedro Carvalho Ragazzi, 201935011

public interface Livro {

    String getNome();
    Period chamadaTempoEmprestimo();
    float chamadaPrecoLocacao();
    float chamadaTaxaMultaDia();
    float chamadaTaxaDanoOuPerda();
    void adicionar(Autor autor);
    void remover(Autor autor);
    boolean verificarAutorAdicionado(Autor autor);
    void classificar(Sessao sessao);

}
