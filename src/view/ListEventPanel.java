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

    private EventTableModel tableModel;

    public ListEventPanel(Model model) {

        controller = new ListController(model);
        this.model = model;

        event_window = null;

        // inicializa tabela de listagem vazia
        tableModel = new EventTableModel(new ArrayList<Evento>(), false);

        list_table = new JTable(tableModel);

        list_table.setAutoCreateRowSorter(true);

        list_table.addMouseListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane list_table_scrollpane = new JScrollPane(list_table);

        list_table_scrollpane.setPreferredSize(new Dimension (580, 460));

        add(list_table_scrollpane);
    }

    public void updateListing(String tipo)
    {
        if (tipo.equals("Listar Eventos"))
            tableModel.setListResults(controller.ListEvents(model.getLoggedUser()));
        else if (tipo.equals("Eventos Salvos"))
            tableModel.setListResults(controller.ListSavedEvents(model.getLoggedUser()));
        else if (tipo.equals("Presenças Confirmadas"))
            tableModel.setListResults(controller.ListConfirmedEvents(model.getLoggedUser()));
    }

    public void setShowDenuncias(boolean showDenuncias)
    {
        tableModel.setShowDenuncias(showDenuncias);
    }

    public void closeSecondaryWindow()
    {
        if (event_window != null)
            event_window.close();
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = list_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && list_table.getSelectedRow() != -1) {
            Evento result = tableModel.getListResults().get(row);

            if (!model.isEventoOnList(result.getID()))
            {
                JOptionPane.showMessageDialog(null, "Evento inexistente!");
                return;
            }
            
            if ((event_window != null) && (event_window.isVisible()))
                event_window.close(); // se tem uma janela já aberta, fecha

            if (model.getLoggedUser().isAdmin())
                event_window = new AdminEventWindow(result, model);
            else if (model.getLoggedUser().isMeuEvento(result.getID()))
                event_window = new ManagerEventWindow(result, model);
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
