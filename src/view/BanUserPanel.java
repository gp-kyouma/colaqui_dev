package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.ListController;
import controller.RemoveUserController;
import model.Model;
import model.Usuario;

public class BanUserPanel extends JPanel implements MouseListener {

    private MainWindow window;
    
    private RemoveUserController controller;
    private ListController list_controller;
    
    private JTable users_table;

    private UsuarioTableModel tableModel;

    public BanUserPanel(Model model, MainWindow window) {

        this.window = window;
        
        controller = new RemoveUserController(model);
        list_controller = new ListController(model);

        tableModel = new UsuarioTableModel(list_controller.ListNonAdmins(),null);

        users_table = new JTable(tableModel);

        users_table.setAutoCreateRowSorter(true);

        users_table.addMouseListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane users_table_scrollpane = new JScrollPane(users_table);

        users_table_scrollpane.setPreferredSize(new Dimension (580, 460));

        add(users_table_scrollpane);
    }

    public void updateListing()
    {
        tableModel.setListUsuarios(list_controller.ListNonAdmins());
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = users_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && users_table.getSelectedRow() != -1) {
            Usuario usuario = tableModel.getListUsuarios().get(row);

            int n = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja banir esse usuário?","Banir Usuário",JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {
                controller.RemoveUser(usuario);
                window.closeSecondaryWindows();
                updateListing();
            }
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}


