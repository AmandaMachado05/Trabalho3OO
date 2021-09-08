import java.util.ArrayList;
import java.util.List;

public class Controle {

    private  List<Livro> livrosDisponiveis = new ArrayList<Livro>();
    private  List<Livro> livrosIndisponiveis = new ArrayList<Livro>();

    public List<Livro> validarLivrosDesejados(List<Livro> livrosDesejados){
        List<Livro> livrosValidados = new ArrayList<Livro>();
        if(livrosDesejados==null||livrosDesejados.isEmpty()){
            throw new NullPointerException("Lista de livros desejados vazia");
        }
        for (Livro livroDesejado : livrosDesejados) {
            if(!livrosDisponiveis.contains(livroDesejado)) {
                throw new NullPointerException("Livro não está disponível para locação");
            }else if(livrosValidados.contains(livroDesejado)){
                throw new NullPointerException("Pedido de livro duplicado");
            }else{
                livrosValidados.add(livroDesejado);
            }
        }
        return livrosValidados;
    }

    public List<Livro> exibirListaDisponiveis(){
        return livrosDisponiveis;
    }

    public List<Livro> exibirListaIndisponiveis(){
        return livrosIndisponiveis;
    }

    public void adicionarListaDisponiveis(Livro livro){
        livrosDisponiveis.add(livro);
    }

    public void removerListaDisponiveis(Livro livro){
        livrosDisponiveis.remove(livro);
        adicionarListaIndisponiveis(livro);
    }
    public void adicionarListaIndisponiveis(Livro livro){
        livrosIndisponiveis.add(livro);
    }

    public void removerListaIndisponiveis(Livro livro){
        livrosIndisponiveis.remove(livro);
        adicionarListaDisponiveis(livro);
    }

}
