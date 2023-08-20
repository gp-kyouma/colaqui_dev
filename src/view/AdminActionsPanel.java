package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminActionsPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    
    private JButton search_event_button;
    private JLabel user_label;

    private JButton list_events_button;
    private JButton ban_user_button;

    private JButton notifs_button;
    private JButton logout_button;

    public AdminActionsPanel(MainWindow window)
    {
        this.window = window;
        
        user_label = new JLabel();

        search_event_button = new JButton ("Pesquisar Evento");
        search_event_button.addActionListener(this);

        setPreferredSize(new Dimension (200, 480));

        add(user_label);
        add(search_event_button);

        list_events_button = new JButton("Listar Eventos");
        list_events_button.addActionListener(this);
        add(list_events_button);

        ban_user_button = new JButton("Banir Usuário");
        ban_user_button.addActionListener(this);
        add(ban_user_button);

        notifs_button = new JButton ("Notificações");
        notifs_button.addActionListener(this);
        add(notifs_button);

        logout_button = new JButton ("Sair");
        logout_button.addActionListener(this);
        add(logout_button);
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
        else if (s.equals("Banir Usuário")) {
            window.updateCenterPanel("Banir Usuário");
        }
        else if (s.equals("Notificações")) {
            window.updateCenterPanel("Notificações");
        }
        else if (s.equals("Sair")) {
            window.logout();
        }
    }

    public void updateLoggedName(String name)
    {
        user_label.setText("<html>Logado como:<br/>" + name + "<br/>&lt;ADMIN&gt;</html>");
    }
}

