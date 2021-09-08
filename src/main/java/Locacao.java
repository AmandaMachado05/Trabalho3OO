import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Locacao {

    private Cliente cliente;
    private String dataLocacao;
    private float precoLocacaoTotal;
    private final List<Livro> livrosValidados;
    private List<LocalDate> dataDevolucaoEstimada;
    private Devolucao devolucao;

    public Locacao(Cliente cliente,String dataLocacao,List<Livro> livrosDesejados,Controle controle) {
        this.cliente = cliente;
        this.dataLocacao = dataLocacao;
        validarDados();
        livrosValidados=controle.validarLivrosDesejados(livrosDesejados);
        this.dataDevolucaoEstimada = new ArrayList<LocalDate>();
        calcularDataDevolucaoEstimada();
        this.precoLocacaoTotal=calcularPrecoLocacaoTotal();
    }

    public void calcularDataDevolucaoEstimada() {
        for (Livro livroValidado : livrosValidados) {
            dataDevolucaoEstimada.add((formatarDatas(this.dataLocacao)).plus(livroValidado.chamadaTempoEmprestimo()));
        }
    }

    public float calcularPrecoLocacaoTotal(){
        precoLocacaoTotal=0.0f;
        for (Livro livroValidado : livrosValidados) {
            precoLocacaoTotal+=livroValidado.chamadaPrecoLocacao();
        }
        return precoLocacaoTotal;
    }

    public List<LocalDate> getListaDataDevolucaoEstimada(){
        return dataDevolucaoEstimada;
    }

    public LocalDate formatarDatas(String data){
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFormatada = LocalDate.parse(data, formatador);
        return dataFormatada;
    }


    private void validarDados() {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente Inválido");
        }
        if(dataLocacao==null||dataLocacao.isEmpty()){
            throw new IllegalArgumentException("Data de locação vazia");
        }
        if(livrosValidados!=null &&(dataDevolucaoEstimada == null || dataDevolucaoEstimada.isEmpty())){
            throw new IllegalArgumentException("Erro ao estimar datas de devolução");
        }
        if(livrosValidados!=null &&(precoLocacaoTotal==0.0f||precoLocacaoTotal<0.0f)){
            throw new IllegalArgumentException("Erro ao calcular preços de locação");
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (this.cliente != cliente) {
            if (this.cliente != null) {
                this.cliente.setNullLocacao();
            }
            this.cliente = cliente;
            if (this.cliente != null && this.cliente.getLocacao() != this) {
                    this.cliente.setLocacao(this);
            }
        }
    }

    public void setNullCliente() {
        this.cliente = null;
    }

    public String getNomeCliente() {
        if (this.cliente == null) {
            return "Outro cliente é responsável pela locação";
        }
        else {
            return cliente.getNome();
        }
    }


    public void removerListaDisponiveis(Controle controle){
        for (Livro livroValidado : livrosValidados) {
            controle.removerListaDisponiveis(livroValidado);
        }
    }


    public class Devolucao{
        private String dataDevolucao;
        private int qtddDiasAtraso;
        private float taxaTotal;
        private List<Livro> livrosDanificadosOuPerdidos=new ArrayList<Livro>();

        public Devolucao(String dataDevolucao, int qtddDiasAtraso,List<Livro> livrosDanificadosOuPerdidos) {
            this.dataDevolucao = dataDevolucao;
            this.qtddDiasAtraso = qtddDiasAtraso;
            this.livrosDanificadosOuPerdidos = livrosDanificadosOuPerdidos;
            taxaTotal=calcularMulta();

        }

        public float calcularMulta() {
            float taxas = 0.0f;
            if (!dataDevolucaoEstimada.equals(formatarDatas(dataDevolucao))) {
                for (Livro livroValidado : livrosValidados) {
                    if(livrosDanificadosOuPerdidos.contains(livroValidado)){
                        taxas+= livroValidado.chamadaTaxaDanoOuPerda();
                    }else{
                    taxas+= qtddDiasAtraso * livroValidado.chamadaTaxaMultaDia();
                    }
                }
            }
            return taxas;
        }

        public void removerListaIndisponiveis(Controle controle){
            for (Livro livroValidado : livrosValidados) {
                if(!livrosDanificadosOuPerdidos.contains(livroValidado)){
                    controle.removerListaIndisponiveis(livroValidado);
                }
            }
        }

        private void validarDados() {
            if (dataDevolucao.equals(null)) {
                throw new IllegalArgumentException("Data devolução inválida");
            }
            if(qtddDiasAtraso<0){
                throw new IllegalArgumentException("Quantidade de dias de atraso inválida");
            }
            if(livrosDanificadosOuPerdidos==null||livrosDanificadosOuPerdidos.isEmpty()){
                throw new IllegalArgumentException("Lista livros danificados ou perdidos vazia");
            }
        }

    }
}
