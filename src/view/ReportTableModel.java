package view;

// Baseado em
// https://pt.stackoverflow.com/questions/121513/como-popular-uma-jtable-com-tablemodel-pr%C3%B3prio

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

public class ReportTableModel extends AbstractTableModel {
    private String colunas[] = {"Motivo da Denúncia"};
    private ArrayList<String> denuncias;
    private final int COLUNA_MOTIVO = 0;

    public ReportTableModel(ArrayList<String> denuncias) {
        this.denuncias = denuncias;
    }

    // retorna se a célula é editável ou não (no nosso caso não)
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    // retorna o total de itens (que virarão linhas) da nossa lista
    @Override
    public int getRowCount() {
        return denuncias.size();
    }

    // retorna o total de colunas da tabela
    @Override
    public int getColumnCount() {
        return colunas.length;
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
            case COLUNA_MOTIVO:
                return String.class;
            default:
                return String.class;
        }
    }

    // preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String result = this.denuncias.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_MOTIVO:
                return result;
        }
        return null;
    }
    
    public void setListDenuncias(ArrayList<String> denuncias)
    {
        this.denuncias = denuncias;
        fireTableDataChanged();
    }

    public ArrayList<String> getListDenuncias()
    {
        return denuncias;
    }
}

