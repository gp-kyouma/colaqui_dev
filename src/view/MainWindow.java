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

    private JPanel empty_panel; // vazio
    private JPanel search_panel;
    private JPanel register_panel;

    public MainWindow(Model model) {
        
        frame = new JFrame ("ColAqui 0.2.1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        side_panel = new JPanel(new CardLayout());
        center_panel = new JPanel(new CardLayout());

        side_layout   = (CardLayout)(side_panel.getLayout());
        center_layout = (CardLayout)(center_panel.getLayout());

        login_panel = new LoginPanel(model, this);
        user_actions_panel = new UserActionsPanel(this);
        //todo: admin panel

        search_panel = new SearchEventPanel(model);
        register_panel = new UserRegisterPanel(model,this);

        empty_panel = new JPanel();
        empty_panel.setPreferredSize(new Dimension (600, 480));    

        side_panel.add(login_panel, "LOGIN_PANEL");
        side_panel.add(user_actions_panel, "USER_PANEL");
        //todo: admin panel

        center_panel.add(empty_panel, "<Vazio>");
        center_panel.add(search_panel, "Pesquisar Evento");
        center_panel.add(register_panel, "Cadastrar");

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

    public void updateSidePanel(String command)
    {
        if (command.equals("LOGIN_PANEL")) {
            side_layout.show(side_panel, "LOGIN_PANEL");
        }
        else if (command.equals("USER_PANEL")) {
            side_layout.show(side_panel, "USER_PANEL");
        }
        else if (command.equals("ADMIN_PANEL")) {
            //side_layout.show(side_panel, "ADMIN_PANEL");    //does not yet exist
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
    }
}
