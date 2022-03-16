package mvc.controller.cadastro;

import br.com.taimber.algoritmos.Datas;
import br.com.taimber.arquivos.LeitorDePropriedades;
import br.com.taimber.swings.PopulaCombobox;
import java.util.Map;
import java.util.Properties;
import mvc.model.cadastro.DaoCadastro;
import mvc.view.telas.sistema.ViewSistema;
import sistema.model.BancoFactory;
import sistema.model.Propriedades;

/**
 * Exibe os dados cadastrados de um registro na view
 *
 * @author E-mail(salomao@taimber.com)
 * @version 1.0
 */
public class ControllerCadastroExibe {

    /**
     * Exibe os dados
     *
     * @param view View
     * @param id Id do cadastro
     */
    public static void exibir(ViewSistema view, Object id) {

        /* excessão */
        try {

            /* cadastro */
            DaoCadastro cadastro = new DaoCadastro();

            /* dados */
            Map dados = (Map) cadastro.getDadosCadastro((String) id).get(0);

            /* popula */
            view.jDdata.setDate(Datas.converterStringParaDate((String) dados.get("data")));
            view.jCsituacao.setSelectedItem(dados.get("situacao"));
            view.jTdescricao.setText((String) dados.get("descricao"));
            view.jCcentroCusto.setSelectedItem(dados.get("centrodecusto"));
            view.jCsolicitante.setSelectedItem(dados.get("solicitante"));
            view.jTvalorTotal.setText((String) dados.get("valortotal"));

        } catch (java.lang.IndexOutOfBoundsException ex) {

        }

    }

    /**
     * Exibe os dados
     *
     * @param view View
     */
    public static void exibir(ViewSistema view) {

        /* carrega os comboboxes */
        carregaComboboxes(view);

    }

    /**
     * Carrega os comboboxes necessários
     */
    private static void carregaComboboxes(ViewSistema view) {

        /* propriedades */
        Properties propriedades = new LeitorDePropriedades(Propriedades.ENDERECO_ARQUIVO_CONFIGURACOES).getPropriedades();

        /* popula */
        PopulaCombobox.executa(new BancoFactory(true).getBanco(), view.jCcentroCusto, propriedades.getProperty("prop.server.tabela"), "centrodecusto");
        PopulaCombobox.executa(new BancoFactory(true).getBanco(), view.jCsolicitante, propriedades.getProperty("prop.server.tabela"), "solicitante");
        PopulaCombobox.executa(new BancoFactory(true).getBanco(), view.jCcentroCustoPesquisa, propriedades.getProperty("prop.server.tabela"), "centrodecusto");

    }

}
