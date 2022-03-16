package mvc.controller.cadastro;

import br.com.taimber.algoritmos.Datas;
import br.com.taimber.swings.ValidaCamposNumericos;
import javax.swing.JOptionPane;
import mvc.model.cadastro.BeanCadastro;
import mvc.model.cadastro.DaoCadastro;
import mvc.view.telas.sistema.ViewSistema;

/**
 * Pega os dados da view e grava no banco de dados
 *
 * @author E-mail(salomao@taimber.com)
 * @version 1.0
 */
public class ControllerCadastroGrava {

    /**
     * Grava os dados
     *
     * @param view View
     * @param id ID do cadastro
     * @return True conseguiu gravar os dados
     */
    public static boolean gravar(ViewSistema view, Object id) {

        /* cadastro */
        BeanCadastro cadastro = new BeanCadastro();
        cadastro.setId(id);
        cadastro.setData(Datas.converterDateParaString(view.jDdata.getDate()));
        cadastro.setSituacao(view.jCsituacao.getSelectedItem());
        cadastro.setDescricao(view.jTdescricao.getText());
        cadastro.setCentroDeCusto(view.jCcentroCusto.getEditor().getItem());
        cadastro.setSolicitante(view.jCsolicitante.getEditor().getItem());
        cadastro.setValorTotal(view.jTvalorTotal.getText());

        /* cadastro */
        DaoCadastro cadastroDao = new DaoCadastro();

        /* validad dados */
        if (isDadosValidados(view)) {

            /* grava */
            if (cadastroDao.gravar(cadastro)) {

                /* Informa que o cadastro foi realizado */
                JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");

                /* retorno */
                return true;

            }

        }

        /* retorno */
        return false;

    }

    /* valida os dados */
    private static boolean isDadosValidados(ViewSistema view) {

        /* valida campo de valor total R$ */
        if (!view.jTvalorTotal.getText().isEmpty()) {

            /* retorno */
            if (!ValidaCamposNumericos.isCamposNumericosValidados(view.jTvalorTotal)) {

                /* retorno */
                return false;

            }

        }

        /* retorno */
        return true;

    }

}
