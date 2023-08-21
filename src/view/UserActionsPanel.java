package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserActionsPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    
    private JButton search_event_button;
    private JLabel user_label;

    private JTabbedPane papel_pane;
    private JPanel papel_membro;
    private JPanel papel_gerente;
    private JPanel papel_patrocinador;

    private JButton saved_events_button;
    private JButton confirmed_events_button;
   
    private JButton create_event_button;
    private JButton list_events_button;

    private JButton propose_sponsor_button;
    private JButton sponsored_events_button;

    private JButton notifs_button;
    private JButton logout_button;

    public UserActionsPanel(MainWindow window)
    {
        this.window = window;
        
        user_label = new JLabel();

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

        papel_pane.add("Patrocinar Evento", papel_patrocinador);
        papel_pane.add("Gerenciar Meus Eventos", papel_gerente);
        papel_pane.add("Área do Usuário", papel_membro);

        // para que todas as tabs tenham o mesmo tamanho
        JLabel patrocinio_label = new JLabel("Patrocinar Evento", JLabel.CENTER);
        patrocinio_label.setPreferredSize(new Dimension(156, 16));
        papel_pane.setTabComponentAt(0, patrocinio_label);

        JLabel gerenciar_label = new JLabel("Gerenciar Meus Eventos", JLabel.CENTER);
        gerenciar_label.setPreferredSize(new Dimension(156, 16));
        papel_pane.setTabComponentAt(1, gerenciar_label);

        JLabel area_usuario_label = new JLabel("Área do Usuário", JLabel.CENTER);
        area_usuario_label.setPreferredSize(new Dimension(156, 16));
        papel_pane.setTabComponentAt(2, area_usuario_label);

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

        propose_sponsor_button = new JButton("Propor Patrocínio");
        propose_sponsor_button.addActionListener(this);
        papel_patrocinador.add(propose_sponsor_button);
        sponsored_events_button = new JButton("Eventos Patrocinados");
        sponsored_events_button.addActionListener(this);
        papel_patrocinador.add(sponsored_events_button);

        add(papel_pane);

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
        else if (s.equals("Propor Patrocínio")) {
            window.updateCenterPanel("Propor Patrocínio");
        }
        else if (s.equals("Eventos Patrocinados")) {
            window.updateCenterPanel("Eventos Patrocinados");
        }
        else if (s.equals("Notificações")) {
            window.updateCenterPanel("Notificações");
        }
        else if (s.equals("Sair")) {
            papel_pane.setSelectedComponent(papel_membro);
            window.logout();
        }
    }

    public void updateLoggedName(String name)
    {
        user_label.setText("<html>Logado como:<br/>" + name + "</html>");
    }
}
