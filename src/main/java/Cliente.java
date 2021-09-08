public class Cliente {

    private int codigo;
    private String nome;
    private String cpf;
    private String telefone;
    private Locacao locacao;

    private Endereco endereco;

    public Cliente(int codigo, String nome,String cpf,String telefone, String logradouro, int numero,
                 String complemento, String bairro, String cidade, String uf, String cep) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf= cpf;
        this.telefone = telefone;
        endereco = new Endereco(logradouro, numero, complemento, bairro, cidade, uf, cep);
    }

    public String getNome() {
        return nome;
    }

    public Locacao getLocacao() {
        return locacao;
    }

    public void setLocacao(Locacao locacao) {
        if (this.locacao != locacao) {
            if (this.locacao != null) {
                this.locacao.setNullCliente();
            }
            this.locacao = locacao;
            if (this.locacao != null && this.locacao.getCliente() != this) {
                this.locacao.setCliente(this);
            }
        }
    }

    public void setNullLocacao() {
        this.locacao = null;
    }

    public String getNomeClienteLocacao() {
        if (this.locacao == null) {
            return "Cliente sem pendências";
        }
        else {
            return locacao.getNomeCliente();
        }
    }

    public class Endereco {
        private String logradouro;
        private int numero;
        private String complemento;
        private String bairro;
        private String cidade;
        private String uf;
        private String cep;

        private Endereco(String logradouro, int numero,
                         String complemento, String bairro, String cidade, String uf, String cep) {
            this.logradouro = logradouro;
            this.numero = numero;
            this.complemento = complemento;
            this.bairro = bairro;
            this.cidade = cidade;
            this.uf = uf;
            this.cep = cep;
        }
    }
}
