package mvc.controller.relatorio;

import br.com.taimber.algoritmos.Datas;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import javax.swing.JOptionPane;
import mvc.model.relatorio.SalvarRelatorioPDF;
import mvc.view.telas.sistema.ViewSistema;
import sistema.model.Swap;

public class ControllerRelatorioEventos {

    /**
     * Eventos
     *
     * @param view View
     */
    public static void addEventos(ViewSistema view) {

        /* botão para exportar relatório do cadastro aberto */
        view.jBcadastroExportarPDF.addActionListener((ActionEvent e) -> {

            /* monta o relatório do cadastro aberto */
            relatorioCadastroAberto();

        });

        /* botão para exportar relatório completo */
        view.jBatalhoExportar.addActionListener((ActionEvent e) -> {

            /* monta o relatório de todos os cadastros */
            relatorioCompletoComTodosOsCadastros(view);

        });

    }

    /**
     * Monta o relatório do cadastro aberto
     *
     * @param view
     */
    private static void relatorioCadastroAberto() {

        /* novo relatório */
        SalvarRelatorioPDF relatorio = new SalvarRelatorioPDF();

        /* entidades de pesquisa */
        LinkedHashMap entidades = new LinkedHashMap();
        entidades.put("id", Swap.getIdCadastro());

        /* seta as entidades */
        relatorio.setEntidades(entidades);

        /* valida se salvou o relatório */
        if (relatorio.salvar()) {

            JOptionPane.showMessageDialog(null, "Relatório salvo com sucesso!");

        } else {

            JOptionPane.showMessageDialog(null, "Relatório não salvo!");

        }

    }

    /**
     * Monta o relatório de todos os cadastros
     *
     * @param view
     */
    private static void relatorioCompletoComTodosOsCadastros(ViewSistema view) {

        /* novo relatório */
        SalvarRelatorioPDF relatorio = new SalvarRelatorioPDF();

        /* entidades de pesquisa */
        LinkedHashMap entidades = new LinkedHashMap();
        entidades.put("dataInicial", Datas.converterDateParaString(view.jDdataInicialPesquisa.getDate()));
        entidades.put("dataFinal", Datas.converterDateParaString(view.jDdataFinalPesquisa.getDate()));

        /* valida centro de custo */
        if (view.jCcentroCustoPesquisa.getSelectedIndex() > -1) {

            entidades.put("centrodecusto", view.jCcentroCustoPesquisa.getSelectedItem());

        }

        /* valida situação */
        if (!view.jCsituacaoPesquisa.getSelectedItem().equals("Todos")) {

            entidades.put("situacao", view.jCsituacaoPesquisa.getSelectedItem());

        }

        /* seta as entidades */
        relatorio.setEntidades(entidades);

        /* valida se salvou o relatório */
        if (relatorio.salvar()) {

            JOptionPane.showMessageDialog(null, "Relatório salvo com sucesso!");

        } else {

            JOptionPane.showMessageDialog(null, "Relatório não salvo!");

        }

    }

}
