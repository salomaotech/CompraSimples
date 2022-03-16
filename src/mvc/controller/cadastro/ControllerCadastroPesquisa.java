package mvc.controller.cadastro;

import br.com.taimber.algoritmos.Datas;
import br.com.taimber.algoritmos.FormataParaMoedaBrasileira;
import br.com.taimber.persistencia.sql.SqlCompletaQuery;
import br.com.taimber.persistencia.sql.SqlEntreDatas;
import br.com.taimber.swings.MudaCorLinhaJtable;
import br.com.taimber.swings.Paginador;
import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import javax.swing.table.DefaultTableModel;
import mvc.model.cadastro.DaoCadastro;
import mvc.view.telas.sistema.ViewSistema;
import sistema.model.BancoFactory;
import sistema.model.PesquisaRegistro;

/**
 * Pesquisa os dados e apresenta os resultados na view
 *
 * @author E-mail(salomao@taimber.com)
 * @version 1.0
 */
public class ControllerCadastroPesquisa {

    /**
     * Exibe os dados
     *
     * @param view View
     */
    public static void pesquisar(ViewSistema view) {

        /* entidades */
        Map entidadesPesquisa = new LinkedHashMap();

        /* valida centro de custo */
        if (view.jCcentroCustoPesquisa.getSelectedIndex() > -1) {

            entidadesPesquisa.put("centrodecusto", view.jCcentroCustoPesquisa.getSelectedItem());

        }

        /* valida situação */
        if (!view.jCsituacaoPesquisa.getSelectedItem().equals("Todos")) {

            entidadesPesquisa.put("situacao", view.jCsituacaoPesquisa.getSelectedItem());

        }

        /* completa query */
        SqlCompletaQuery completaQuery = new SqlCompletaQuery(entidadesPesquisa, view.jCcadastroPaginador.getSelectedItem(), new DaoCadastro().getTabela(), true);

        /* colunas a serem pesquisadas */
        List colunasTabela = new ArrayList();
        colunasTabela.add("id");
        colunasTabela.add("data");
        colunasTabela.add("situacao");
        colunasTabela.add("descricao");
        colunasTabela.add("centrodecusto");
        colunasTabela.add("solicitante");
        colunasTabela.add("valortotal");

        /* condição anterior */
        String condicaoAnterior = null;

        /* valida data inicial de pesquisa */
        if (!isNull(view.jDdataInicialPesquisa.getDate())) {

            /* condição anterior */
            condicaoAnterior = SqlEntreDatas.montar(Datas.converterDateParaString(view.jDdataInicialPesquisa.getDate()), null, "data");

        }

        /* valida data inicial e final de pesquisa */
        if (!isNull(view.jDdataInicialPesquisa.getDate()) && !isNull(view.jDdataFinalPesquisa.getDate())) {

            /* condição anterior */
            condicaoAnterior = SqlEntreDatas.montar(Datas.converterDateParaString(view.jDdataInicialPesquisa.getDate()), Datas.converterDateParaString(view.jDdataFinalPesquisa.getDate()), "data");

        }

        /* dados */
        List dados = new PesquisaRegistro().executar(new DaoCadastro().getTabela(), completaQuery, colunasTabela, condicaoAnterior, "order by str_to_date(data, '%d/%m/%Y') asc");

        /* default model */
        DefaultTableModel model = (DefaultTableModel) view.jTcadastroLista.getModel();
        model.setNumRows(0);

        /* contador */
        int contador = 0;

        /* list com cores */
        List cores = new ArrayList();

        /* listando os dados */
        for (Object linha : dados) {

            /* map de dados */
            Map dadosMap = (Map) linha;

            /* valor total */
            String valorTotal = FormataParaMoedaBrasileira.cifrar(dadosMap.get("valortotal"));

            /* objeto linha */
            Object[] linhaJtable = new Object[]{
                dadosMap.get("id"),
                dadosMap.get("data"),
                dadosMap.get("centrodecusto"),
                dadosMap.get("situacao"),
                dadosMap.get("descricao").toString().replace("\n", ", "),
                valorTotal

            };

            /* valida situação */
            switch (String.valueOf(dadosMap.get("situacao"))) {

                case "Finalizada":
                    cores.add(Color.decode("#ace0c6"));
                    break;

                default:
                    cores.add(Color.decode("#f2f2f2"));

            }

            /* insere a linha */
            model.insertRow(contador, linhaJtable);

            /* atualiza o contador */
            contador++;

        }

        /* muda a cor da jtable */
        MudaCorLinhaJtable.mudar(view.jTcadastroLista, cores);

        /* paginador */
        Paginador paginador = new Paginador(view.jCcadastroPaginador, new DaoCadastro().getTabela(), completaQuery.completar(false, null, null));
        paginador.popular(new BancoFactory(true).getBanco());

    }

}
