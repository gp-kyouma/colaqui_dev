package view;

// Baseado em
// https://pt.stackoverflow.com/questions/121513/como-popular-uma-jtable-com-tablemodel-pr%C3%B3prio

import javax.swing.table.AbstractTableModel;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Notificacao;

public class NotificationTableModel extends AbstractTableModel {

    private String colunas[] = {"Notificação", "Data"};
    private ArrayList<Notificacao> notifs;
    private final int COLUNA_NOME = 0;
    private final int COLUNA_DATA = 1;

    public NotificationTableModel(ArrayList<Notificacao> notifs) {
        this.notifs = notifs;
    }

    // retorna se a célula é editável ou não (no nosso caso não)
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    // retorna o total de itens (que virarão linhas) da nossa lista
    @Override
    public int getRowCount() {
        return notifs.size();
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
            case COLUNA_NOME:
                return String.class;
            case COLUNA_DATA:
                return LocalDate.class;
            default:
                return String.class;
        }
    }

    // preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Notificacao result = this.notifs.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_NOME:
                return result.getNotificacao();
            case COLUNA_DATA:
                return result.getTime();
        }
        return null;
    }

    // atualiza tabela
    public void setNotifs(ArrayList<Notificacao> notifs)
    {
        this.notifs = notifs;
        fireTableDataChanged();
    }

    public ArrayList<Notificacao> getNotifs()
    {
        return notifs;
    }
}
