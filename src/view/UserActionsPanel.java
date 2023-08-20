package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserActionsPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    
    private JButton search_event_button;
    private JLabel wip_label;
    private JLabel user_label;

    private JTabbedPane papel_pane;
    private JPanel papel_membro;
    private JPanel papel_gerente;
    private JPanel papel_patrocinador;

    private JButton saved_events_button;
    private JButton confirmed_events_button;
   
    private JButton create_event_button;
    private JButton list_events_button;

    public UserActionsPanel(MainWindow window)
    {
        this.window = window;
        
        user_label = new JLabel();
        wip_label = new JLabel ("WIP: outras funcionalidades"); // remove later obviously

        search_event_button = new JButton ("Pesquisar Evento");
        search_event_button.addActionListener(this);

        setPreferredSize(new Dimension (200, 480));

        add(user_label);
        add(search_event_button);

        papel_membro = new JPanel();
        papel_gerente = new JPanel();
        papel_patrocinador = new JPanel();

        papel_membro.setPreferredSize(new Dimension (180, 70));
        papel_gerente.setPreferredSize(new Dimension (180, 70));
        papel_patrocinador.setPreferredSize(new Dimension (180, 70));

        papel_pane = new JTabbedPane();

        papel_pane.add("Patrocinador", papel_patrocinador);
        papel_pane.add("Gerente de Evento", papel_gerente);
        papel_pane.add("Membro Acadêmico", papel_membro);

        papel_pane.setSelectedComponent(papel_membro);

        saved_events_button = new JButton("Eventos Salvos");
        saved_events_button.addActionListener(this);
        papel_membro.add(saved_events_button);
        confirmed_events_button = new JButton("Presenças Confirmadas");
        confirmed_events_button.addActionListener(this);
        papel_membro.add(confirmed_events_button);

        create_event_button = new JButton("Criar Evento");
        create_event_button.addActionListener(this);
        papel_gerente.add(create_event_button);
        list_events_button = new JButton("Listar Meus Eventos");
        list_events_button.addActionListener(this);
        papel_gerente.add(list_events_button);

        papel_patrocinador.add(new JLabel("PATROCINADOR TEST"));
        // todo: all of this... lol

        add(papel_pane);
        add(wip_label);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Pesquisar Evento")) {
            window.updateCenterPanel("Pesquisar Evento");
        }
        else if (s.equals("Criar Evento")) {
            window.updateCenterPanel("Criar Evento");
        }
        else if (s.equals("Listar Meus Eventos")) {
            window.updateCenterPanel("Listar Eventos");
        }
        else if (s.equals("Eventos Salvos")) {
            window.updateCenterPanel("Eventos Salvos");
        }
        else if (s.equals("Presenças Confirmadas")) {
            window.updateCenterPanel("Presenças Confirmadas");
        }
    }

    public void updateLoggedName(String name)
    {
        user_label.setText("<html>Logado como:<br/>" + name + "</html>");
    }
}
