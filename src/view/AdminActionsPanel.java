package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminActionsPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    
    private JButton search_event_button;
    private JLabel wip_label;
    private JLabel user_label;

    private JButton list_events_button;

    public AdminActionsPanel(MainWindow window)
    {
        this.window = window;
        
        user_label = new JLabel();
        wip_label = new JLabel ("WIP: outras funcionalidades"); // remove later obviously

        search_event_button = new JButton ("Pesquisar Evento");
        search_event_button.addActionListener(this);

        setPreferredSize(new Dimension (200, 480));

        add(user_label);
        add(search_event_button);

        list_events_button = new JButton("Listar Eventos");
        list_events_button.addActionListener(this);

        add(list_events_button);

        add(wip_label);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Pesquisar Evento")) {
            window.updateCenterPanel("Pesquisar Evento");
        }
        else if (s.equals("Listar Eventos")) {
            window.updateCenterPanel("Listar Eventos");
        }
    }

    public void updateLoggedName(String name)
    {
        user_label.setText("<html>Logado como:<br/>" + name + "<br/>&lt;ADMIN&gt;</html>");
    }
}

