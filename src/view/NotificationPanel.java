package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import controller.NotificationController;
import model.Model;
import model.Notificacao;

public class NotificationPanel extends JPanel implements MouseListener {

    private NotificationController controller;

    private Model model;
    
    private JTable notif_table;

    private NotificationTableModel tableModel;

    public NotificationPanel(Model model) {

        controller = new NotificationController(model);
        this.model = model;

        tableModel = new NotificationTableModel(new ArrayList<Notificacao>());

        notif_table = new JTable(tableModel);

        notif_table.addMouseListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane notif_table_scrollpane = new JScrollPane(notif_table);

        notif_table_scrollpane.setPreferredSize(new Dimension (580, 460));

        add(notif_table_scrollpane);
    }

    public void updateListing()
    {
        tableModel.setNotifs(model.getLoggedUser().getNotificacoes());
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = notif_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && notif_table.getSelectedRow() != -1) {
            int indice = row;

            Notificacao notif = model.getLoggedUser().getNotificacoes().get(indice);

            int n = JOptionPane.showConfirmDialog(null,
                                                    "<html><body><p style='width: 200px;'>"
                                                    +notif.getNotificacao()
                                                    +"<br/><br/>Deseja excluir essa notificação?</p></body></html>",
                                                    "Notificação",
                                                    JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {
                controller.RemoveNotification(model.getLoggedUser(), indice);
                tableModel.setNotifs(model.getLoggedUser().getNotificacoes());
            }
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

