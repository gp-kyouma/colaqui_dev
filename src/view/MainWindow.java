package view;

import java.awt.*;
import javax.swing.*;

import model.Model;

public class MainWindow {
    
    // atributos referentes ao GUI
    private JFrame frame;

    private JPanel user_actions_panel;

    private JPanel empty_panel; // placeholder panel

    private JPanel search_panel;

    public MainWindow(Model model) {
        
        frame = new JFrame ("ColAqui 0.1.2.2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        user_actions_panel = new UserActionsPanel(this);
        search_panel = new SearchEventPanel(model);

        empty_panel = new JPanel();
        empty_panel.setPreferredSize(new Dimension (600, 480));    
    }

    public void show() {

        Container pane = frame.getContentPane();

        // initial left panel will be login panel (when that becomes a thing that exists)
        pane.add(user_actions_panel,BorderLayout.LINE_START);
        pane.add(empty_panel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible (true);
        frame.setResizable(false);
    }

    public void updateCenterPanel(String command)
    {
        if (command.equals("Pesquisar Evento")) {
            
            Container pane = frame.getContentPane();
            BorderLayout layout = (BorderLayout) pane.getLayout();
            JPanel center_panel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);

            if (!search_panel.isValid())
            {
                pane.remove(center_panel);
                pane.add(search_panel,BorderLayout.CENTER);
                pane.validate();
            }
        }
    }
}
