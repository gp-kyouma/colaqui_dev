package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.SearchEventController;
import model.Evento;
import model.Model;

public class SearchEventPanel extends JPanel implements ActionListener {

    private SearchEventController controller;
    
    private JTextField search_text_box;
    private JButton search_keyword_button;
    private JButton search_manager_button;
    private JTable search_table;

    private SearchEventTableModel tableModel;

    public SearchEventPanel(Model model) {

        controller = new SearchEventController(model);

        // inicializa tabela de pesquisa com lista vazia (não foi feita nenhuma pesquisa ainda)
        tableModel = new SearchEventTableModel(new ArrayList<Evento>());

        search_text_box = new JTextField (50);
        search_keyword_button = new JButton ("Pesquisar por Sentença Chave");
        search_manager_button = new JButton ("Pesquisar por Gerente de Evento");
        search_table = new JTable(tableModel);

        search_table.setAutoCreateRowSorter(true);

        search_keyword_button.addActionListener(this);
        search_manager_button.addActionListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane search_table_scrollpane = new JScrollPane(search_table);

        search_table_scrollpane.setPreferredSize(new Dimension (580, 400));

        add(search_text_box);
        add(search_keyword_button);
        add(search_manager_button);
        add(search_table_scrollpane);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Pesquisar por Sentença Chave")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = controller.searchEventByKeyword(search_key);

            tableModel.setSearchResults(searchResults);
        }
        else if (s.equals("Pesquisar por Gerente de Evento")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = controller.searchEventByManager(search_key);

            tableModel.setSearchResults(searchResults);
        }
    }
}
