package view;

import java.awt.*;
import javax.swing.*;

import model.Model;

public class MainWindow {
    
    // atributos referentes ao GUI
    private JFrame frame;

    private JPanel side_panel;
    private JPanel center_panel;

    private CardLayout side_layout;
    private CardLayout center_layout;

    private JPanel login_panel;
    private JPanel user_actions_panel;
    private JPanel admin_actions_panel;

    private JPanel empty_panel; // vazio
    private JPanel search_panel;
    private JPanel register_panel;
    private JPanel create_event_panel;
    private JPanel list_events_panel;
    //ban user
    //patrocinio 1
    //patrocinio 2
    private JPanel notifs_panel;

    private Model model;

    public MainWindow(Model model) {
        
        this.model = model;
        
        frame = new JFrame ("ColAqui 0.8.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        side_panel = new JPanel(new CardLayout());
        center_panel = new JPanel(new CardLayout());

        side_layout   = (CardLayout)(side_panel.getLayout());
        center_layout = (CardLayout)(center_panel.getLayout());

        login_panel = new LoginPanel(model, this);
        user_actions_panel = new UserActionsPanel(this);
        admin_actions_panel = new AdminActionsPanel(this);

        search_panel = new SearchEventPanel(model);
        register_panel = new UserRegisterPanel(model,this);
        create_event_panel = new CreateEventPanel(model, this);
        list_events_panel = new ListEventPanel(model);
        //ban user
        //patrocinio 1
        //patrocinio 2
        notifs_panel = new NotificacaoPanel(model);

        empty_panel = new JPanel();
        empty_panel.setPreferredSize(new Dimension (600, 480));    

        side_panel.add(login_panel, "LOGIN_PANEL");
        side_panel.add(user_actions_panel, "USER_PANEL");
        side_panel.add(admin_actions_panel, "ADMIN_PANEL");

        center_panel.add(empty_panel, "<Vazio>");
        center_panel.add(search_panel, "Pesquisar Evento");
        center_panel.add(register_panel, "Cadastrar");
        center_panel.add(create_event_panel, "Criar Evento");
        center_panel.add(list_events_panel, "Listar Eventos");
        //ban user
        //patrocinio 1
        //patrocinio 2
        center_panel.add(notifs_panel, "Notificações");

        // paineis iniciais
        side_layout.show(side_panel, "LOGIN_PANEL");
        center_layout.show(center_panel, "<Vazio>");
    }

    public void show() {

        Container pane = frame.getContentPane();

        pane.add(side_panel,BorderLayout.LINE_START);
        pane.add(center_panel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible (true);
        frame.setResizable(false);
    }

    public void updateSidePanel(String command, String username)
    {
        if (command.equals("LOGIN_PANEL")) {
            side_layout.show(side_panel, "LOGIN_PANEL");
        }
        else if (command.equals("USER_PANEL")) {
            side_layout.show(side_panel, "USER_PANEL");
            ((UserActionsPanel)user_actions_panel).updateLoggedName(username);
        }
        else if (command.equals("ADMIN_PANEL")) {
            side_layout.show(side_panel, "ADMIN_PANEL");
            ((AdminActionsPanel)admin_actions_panel).updateLoggedName(username);
        }
    }
    
    public void updateCenterPanel(String command)
    {
        if (command.equals("<Vazio>")) {
            center_layout.show(center_panel, "<Vazio>");
        }
        else if (command.equals("Pesquisar Evento")) {
            center_layout.show(center_panel, "Pesquisar Evento");
        }
        else if (command.equals("Cadastrar")) {
            center_layout.show(center_panel, "Cadastrar");
        }
        else if (command.equals("Criar Evento")) {
            center_layout.show(center_panel, "Criar Evento");
        }
        else if (command.equals("Listar Eventos")) {
            center_layout.show(center_panel, "Listar Eventos");
            ((ListEventPanel)list_events_panel).updateListing("Listar Eventos");
            ((ListEventPanel)list_events_panel).setShowDenuncias(true);
        }
        else if (command.equals("Eventos Salvos")) {
            center_layout.show(center_panel, "Listar Eventos");
            ((ListEventPanel)list_events_panel).updateListing("Eventos Salvos");
            ((ListEventPanel)list_events_panel).setShowDenuncias(false);
        }
        else if (command.equals("Presenças Confirmadas")) {
            center_layout.show(center_panel, "Listar Eventos");
            ((ListEventPanel)list_events_panel).updateListing("Presenças Confirmadas");
            ((ListEventPanel)list_events_panel).setShowDenuncias(false);
        }
        //3...
        else if (command.equals("Notificações")) {
            center_layout.show(center_panel, "Notificações");
            ((NotificacaoPanel)notifs_panel).updateListing();
        }
    }

    public void logout()
    {
        // se tem alguma janela secundária aberta, fecha
        ((ListEventPanel)list_events_panel).closeSecondaryWindow();
        ((SearchEventPanel)search_panel).closeSecondaryWindow();

        model.logout();

        // volta para paineis iniciais
        side_layout.show(side_panel, "LOGIN_PANEL");
        center_layout.show(center_panel, "<Vazio>");
    }
}
