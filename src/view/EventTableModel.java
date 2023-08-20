package view;

// Baseado em
// https://pt.stackoverflow.com/questions/121513/como-popular-uma-jtable-com-tablemodel-pr%C3%B3prio

import javax.swing.table.AbstractTableModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import model.Evento;

public class EventTableModel extends AbstractTableModel {

    private String colunas[] = {"Nome", "Gerente", "Local", "Data", "Horário", "Vagas ocupadas", "Vagas disponíveis", "Vagas totais", "Avaliação", "Denúncias"};
    private ArrayList<Evento> listResults;
    private boolean showDenuncias;
    private final int COLUNA_NOME = 0;
    private final int COLUNA_GERENTE = 1;
    private final int COLUNA_LOCAL = 2;
    private final int COLUNA_DATA = 3;
    private final int COLUNA_HORARIO = 4;
    private final int COLUNA_OCUPADAS = 5;
    private final int COLUNA_DISPONIVEIS = 6;
    private final int COLUNA_TOTAIS = 7;
    private final int COLUNA_AVALIACAO = 8;
    private final int COLUNA_DENUNCIAS = 9;

    public EventTableModel(ArrayList<Evento> listResults, boolean showDenuncias) {
        this.listResults = listResults;
        this.showDenuncias = showDenuncias;
    }

    // retorna se a célula é editável ou não (no nosso caso não)
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    // retorna o total de itens (que virarão linhas) da nossa lista
    @Override
    public int getRowCount() {
        return listResults.size();
    }

    // retorna o total de colunas da tabela
    @Override
    public int getColumnCount() {
        return colunas.length - (showDenuncias ? 0 : 1);
    }

    // retorna o nome da coluna de acordo com seu indice
    @Override
    public String getColumnName(int indice) {
        return colunas[indice];
    }

    // determina o tipo de dado da coluna conforme seu indice
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COLUNA_NOME:
                return String.class;
            case COLUNA_GERENTE:
                return String.class;
            case COLUNA_DATA:
                return LocalDate.class;
            case COLUNA_HORARIO:
                return LocalTime.class;
            case COLUNA_LOCAL:
                return String.class;
            case COLUNA_OCUPADAS:
                return Integer.class;
            case COLUNA_DISPONIVEIS:
                return Integer.class;
            case COLUNA_TOTAIS:
                return Integer.class;
            case COLUNA_AVALIACAO:
                return Float.class;
            case COLUNA_DENUNCIAS:
                return Integer.class;
            default:
                return String.class;
        }
    }

    // preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Evento result = this.listResults.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_NOME:
                return result.getNome();
            case COLUNA_GERENTE:
                return result.getGerente();
            case COLUNA_DATA:
                return result.getData();
            case COLUNA_HORARIO:
                return result.getHorario();
            case COLUNA_LOCAL:
                return result.getLocal();
            case COLUNA_OCUPADAS:
                return result.getVagasOcupadas();
            case COLUNA_DISPONIVEIS:
                return result.getVagasDisponiveis();
            case COLUNA_TOTAIS:
                return result.getMaxVagas();
            case COLUNA_AVALIACAO:
                return result.getMediaAvaliacoes();
            case COLUNA_DENUNCIAS:
                return result.getNumDenuncias();
        }
        return null;
    }

    // atualiza tabela
    public void setListResults(ArrayList<Evento> listResults)
    {
        this.listResults = listResults;
        fireTableDataChanged();
    }

    public ArrayList<Evento> getListResults()
    {
        return listResults;
    }

    public void setShowDenuncias(boolean showDenuncias)
    {
        this.showDenuncias = showDenuncias;
        fireTableStructureChanged();
    }
}