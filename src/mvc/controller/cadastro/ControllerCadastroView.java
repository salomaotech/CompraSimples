package mvc.controller.cadastro;

import static java.util.Objects.isNull;
import mvc.view.telas.sistema.ViewSistema;
import sistema.model.Swap;

/**
 * Realiza operações na view como (Resetar os controles, Limpar, habilitar
 * campos etc).
 *
 * @author E-mail(salomao@taimber.com)
 * @version 1.0
 */
public class ControllerCadastroView {

    /**
     * Reseta os controles
     *
     * @param view
     */
    public static void resetaControles(ViewSistema view) {

        /* limpa campos */
        view.jDdata.setDate(null);
        view.jCcentroCusto.setSelectedIndex(-1);
        view.jTdescricao.setText("");
        view.jCsituacao.setSelectedIndex(0);
        view.jCsolicitante.setSelectedIndex(-1);
        view.jTvalorTotal.setText("");

    }

    /**
     * Habilita controles
     *
     * @param view View
     */
    public static void habilitaControles(ViewSistema view) {

        /* habilita */
        view.jBcadastroExcluir.setEnabled(!isNull(Swap.getIdCadastro()));
        view.jBcadastroExportarPDF.setEnabled(!isNull(Swap.getIdCadastro()));

    }

    /**
     * Limpa resultados antigos
     *
     * @param view View
     */
    public static void limpaListaResultadosAntigos(ViewSistema view) {

    }

}
