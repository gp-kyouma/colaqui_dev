package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.SearchEventController;
import model.Evento;
import model.Model;

public class SearchEventPanel extends JPanel implements ActionListener, MouseListener {

    private SearchEventController controller;

    private Model model;

    private SecondaryWindow event_window;
    
    private JTextField search_text_box;
    private JButton search_keyword_button;
    private JButton search_manager_button;
    private JTable search_table;

    private SearchEventTableModel tableModel;

    public SearchEventPanel(Model model) {

        controller = new SearchEventController(model);
        this.model = model;

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

        search_table.addMouseListener(this);

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

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = search_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && search_table.getSelectedRow() != -1) {
            Evento result = tableModel.getSearchResults().get(row);

            if ((event_window != null) && (event_window.isVisible()))
                event_window.close(); // se tem uma janela já aberta, fecha

            if (model.getLoggedUser().isAdmin())
                event_window = new UserEventWindow(result, model);//change to AdminEventWindow when that exists
            else if (model.getLoggedUser().isMeuEvento(result.getID()))
                event_window = new UserEventWindow(result, model);//change to ManagerEventWindow when that exists
            else
                event_window = new UserEventWindow(result, model);
            
            event_window.show();
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
