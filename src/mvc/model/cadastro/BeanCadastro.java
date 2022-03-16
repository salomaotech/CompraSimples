package mvc.model.cadastro;

/**
 * Bean que representa um registro no banco de dados
 *
 * @author E-mail(salomao@taimber.com)
 * @version 1.0
 */
public class BeanCadastro {

    private Object id;
    private Object data;
    private Object situacao;
    private Object descricao;
    private Object centroDeCusto;
    private Object solicitante;
    private Object valorTotal;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getSituacao() {
        return situacao;
    }

    public void setSituacao(Object situacao) {
        this.situacao = situacao;
    }

    public Object getDescricao() {
        return descricao;
    }

    public void setDescricao(Object descricao) {
        this.descricao = descricao;
    }

    public Object getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(Object centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public Object getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Object solicitante) {
        this.solicitante = solicitante;
    }

    public Object getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Object valorTotal) {
        this.valorTotal = valorTotal;
    }

}
