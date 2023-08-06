package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserActionsPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    
    private JButton search_event_button;
    private JLabel wip_label;

    public UserActionsPanel(MainWindow window)
    {
        this.window = window;
        
        wip_label = new JLabel ("WIP: outras funcionalidades");
        search_event_button = new JButton ("Pesquisar Evento");

        search_event_button.addActionListener(this);

        setPreferredSize(new Dimension (200, 480));

        add(search_event_button);
        add(wip_label);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Pesquisar Evento")) {
            window.updateCenterPanel("Pesquisar Evento");
        }
    }
}
