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

    private UserEventWindow event_window;
    
    private JTextField search_text_box;
    private JButton search_keyword_button;
    private JButton search_manager_button;
    private JTable search_table;

    private SearchEventTableModel tableModel;

    public SearchEventPanel(Model model) {

        controller = new SearchEventController(model);

        event_window = null;

        // inicializa tabela de pesquisa com lista vazia (não foi feita nenhuma pesquisa ainda)
        tableModel = new SearchEventTableModel(new ArrayList<Evento>());

        search_text_box = new JTextField (50);
        search_keyword_button = new JButton ("Pesquisar por Sentença Chave");
        search_manager_button = new JButton ("Pesquisar por Gerente de Evento");
        search_table = new JTable(tableModel);

        search_table.setAutoCreateRowSorter(true);

        search_keyword_button.addActionListener(this);
        search_manager_button.addActionListener(this);

        search_table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                SearchEventTableModel tmodel = (SearchEventTableModel)table.getModel();

                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Evento result = tmodel.getSearchResults().get(row);
                    // todo: abrir janelas diferentes dependendo se for admin, criador...
                    event_window = new UserEventWindow(result, model);
                    event_window.show();
                    // this is bullshit probably
                }
            }
        });

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
