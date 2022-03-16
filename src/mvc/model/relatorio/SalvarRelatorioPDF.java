package mvc.model.relatorio;

import br.com.taimber.algoritmos.Datas;
import br.com.taimber.algoritmos.FormataParaBigDecimal;
import br.com.taimber.algoritmos.FormataParaMoedaBrasileira;
import br.com.taimber.arquivos.GerarPdf;
import br.com.taimber.arquivos.DialogoSalvarArquivo;
import br.com.taimber.persistencia.sql.SqlCompletaQuery;
import br.com.taimber.persistencia.sql.SqlEntreDatas;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import mvc.model.cadastro.DaoCadastro;
import sistema.model.BancoFactory;

public class SalvarRelatorioPDF {

    private LinkedHashMap entidades;

    /**
     * Atualiza lista de entidades a serem pesquisadas
     *
     * @param entidades Entidades a serem pesquisadas
     */
    public void setEntidades(LinkedHashMap entidades) {

        /* popula entidades */
        this.entidades = entidades;

    }

    /**
     * Monta condição de pesquisa
     *
     * @return String com condição de pesquisa
     */
    private String montaCondicaoPesquisa() {

        /* data inicial e final */
        String dataInicial = (String) this.entidades.get("dataInicial");
        String dataFinal = (String) this.entidades.get("dataFinal");

        /* condição anterior */
        String condicaoAnterior = null;

        /* valida data inicial de pesquisa */
        if (!isNull(dataInicial)) {

            /* condição anterior */
            condicaoAnterior = SqlEntreDatas.montar(dataInicial, null, "data");

        }

        /* valida data inicial e final de pesquisa */
        if (!isNull(dataInicial) && !isNull(dataFinal)) {

            /* condição anterior */
            condicaoAnterior = SqlEntreDatas.montar(dataInicial, dataFinal, "data");

        }

        /* remove entidades */
        this.entidades.remove("dataInicial");
        this.entidades.remove("dataFinal");

        /* completa query */
        SqlCompletaQuery completaQuery = new SqlCompletaQuery(this.entidades, 0, new DaoCadastro().getTabela(), true);

        /* retorno */
        return completaQuery.completar(false, condicaoAnterior, "order by str_to_date(data, '%d/%m/%Y') asc");

    }

    /**
     * Salva o relatório
     *
     * @return True conseguiu salvar o relatório
     */
    public boolean salvar() {

        /* endereço onde será salvo o arquivo */
        String enderecoSalvar = DialogoSalvarArquivo.executar();

        /* valida endereço para salvar arquivo */
        if (isNull(enderecoSalvar)) {

            /* retorno */
            return false;

        }

        /* banco */
        BancoFactory banco = new BancoFactory(true);

        /* dados */
        List dados = banco.getBanco().consultarRegistro("select *from " + new DaoCadastro().getTabela() + " " + montaCondicaoPesquisa());

        /* novo PDF */
        GerarPdf pdf = new GerarPdf(enderecoSalvar);

        /* valor total */
        BigDecimal valorTotal = new BigDecimal("0");

        /* listando os dados */
        for (Object linha : dados) {

            /* map de dados */
            Map dadosMap = (Map) linha;

            /* valor total */
            valorTotal = valorTotal.add(FormataParaBigDecimal.formatar(dadosMap.get("valortotal")));

            /* conteudo */
            String conteudo = ""
                    + "CODIGO: " + dadosMap.get("id")
                    + "\n"
                    + "Data: " + dadosMap.get("data")
                    + "  |  "
                    + "Situacao: " + dadosMap.get("situacao")
                    + "  |  "
                    + "Centro de custo: " + dadosMap.get("centrodecusto")
                    + "\n"
                    + "Valor total : " + FormataParaMoedaBrasileira.cifrar(dadosMap.get("valortotal"))
                    + "\n"
                    + "Solicitante: " + dadosMap.get("solicitante")
                    + "\n"
                    + "\n"
                    + "DESCRITIVO ***"
                    + "\n"
                    + "Descrição: " + dadosMap.get("descricao")
                    + "\n"
                    + "-----------------------------------------------------------------------------------------"
                    + "\n"
                    + "\n";

            /* add linha */
            pdf.addLinha(conteudo);

        }

        /* titulo */
        String título = ""
                + "Relatório de compras"
                + "\n"
                + "Data do relatório: " + Datas.getDataAtualDiaMesAnoComHoraMinutoSegundo()
                + "\n"
                + "Total: " + FormataParaMoedaBrasileira.cifrar(valorTotal)
                + "";

        /* gera o PDF */
        pdf.gerar(título);

        /* retorno */
        return true;

    }

}
