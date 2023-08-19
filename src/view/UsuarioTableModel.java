package view;

// Baseado em
// https://pt.stackoverflow.com/questions/121513/como-popular-uma-jtable-com-tablemodel-pr%C3%B3prio

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

import model.Usuario;

public class UsuarioTableModel extends AbstractTableModel {
    private String colunas[] = {"Nome"};
    private ArrayList<Usuario> usuarios;
    private final int COLUNA_NOME = 0;

    public UsuarioTableModel(ArrayList<Usuario> usuarios, String nomeColuna) {
        this.usuarios = usuarios;
        if (nomeColuna != null)
            colunas[0] = nomeColuna;
    }

    // retorna se a célula é editável ou não (no nosso caso não)
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    // retorna o total de itens (que virarão linhas) da nossa lista
    @Override
    public int getRowCount() {
        return usuarios.size();
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
            default:
                return String.class;
        }
    }

    // preenche cada célula da tabela
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario result = this.usuarios.get(rowIndex);

        switch (columnIndex) {
            case COLUNA_NOME:
                return result.getNome();
        }
        return null;
    }
    
    public void setListUsuarios(ArrayList<Usuario> presencas)
    {
        this.usuarios = presencas;
        fireTableDataChanged();
    }

    public ArrayList<Usuario> getListUsuarios()
    {
        return usuarios;
    }
}
