package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.ListController;
import model.Evento;
import model.Model;

public class ListEventPanel extends JPanel implements MouseListener {

    private ListController controller;

    private Model model;

    private SecondaryWindow event_window;
    
    private JTable list_table;

    private ListEventTableModel tableModel;

    public ListEventPanel(Model model) {

        controller = new ListController(model);
        this.model = model;

        event_window = null;

        // inicializa tabela de listagem vazia
        tableModel = new ListEventTableModel(new ArrayList<Evento>());

        list_table = new JTable(tableModel);

        list_table.setAutoCreateRowSorter(true);

        list_table.addMouseListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane list_table_scrollpane = new JScrollPane(list_table);

        list_table_scrollpane.setPreferredSize(new Dimension (580, 460));

        add(list_table_scrollpane);
    }

    public void updateListing()
    {
        tableModel.setListResults(controller.ListEvents(model.getLoggedUser()));
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = list_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && list_table.getSelectedRow() != -1) {
            Evento result = tableModel.getListResults().get(row);

            if ((event_window != null) && (event_window.isVisible()))
                event_window.close(); // se tem uma janela já aberta, fecha

            if (model.getLoggedUser().isAdmin())
                event_window = new AdminEventWindow(result, model, tableModel);
            else if (model.getLoggedUser().isMeuEvento(result.getID()))
                event_window = new ManagerEventWindow(result, model, tableModel);
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
