package view;

import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import java.awt.*;
import javax.swing.*;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

import controller.SponsorController;
import controller.UserEventController;
import model.Evento;
import model.Model;
import model.Usuario;

public class UserEventWindow extends SecondaryWindow implements ActionListener, WindowListener {
    
    private JPanel panel;

    private Evento evento;
    private Model model;

    private UserEventController controller;

    private JLabel nome_evento;
    private JLabel nome_gerente;
    private JTextArea descricao;
    private JLabel local;
    private JLabel data;
    private JLabel horario;
    private JLabel vagas_totais;
    private JLabel vagas_restantes;
    private JLabel avaliacao_media;

    private JButton presenca_button;
    private JButton avaliar_button;
    private JButton denunciar_button;
    private JButton salvar_button;
    private JButton compartilhar_button;

    private JSlider avaliacao_nota;

    private JTable patrocinios_table;

    public UserEventWindow(Evento evento, Model model)
    {
        frame = new JFrame (evento.getNome());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.evento = evento;
        this.model = model;
        controller = new UserEventController(model);

        panel = new JPanel();

        DateTimeFormatter data_formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        DateTimeFormatter hora_formatter = DateTimeFormatter.ofPattern("HH:mm");

        nome_evento = new JLabel (evento.getNome());
        nome_gerente = new JLabel (String.format("Gerente do evento: %s", evento.getGerente()));
        descricao = new JTextArea (evento.getDescricao());
        local = new JLabel (String.format("Local: %s", evento.getLocal()));
        data = new JLabel (String.format("Data: %s", data_formatter.format(evento.getData())));
        horario = new JLabel(String.format("Horário: %s", hora_formatter.format(evento.getHorario())));
        vagas_totais = new JLabel(String.format("Vagas totais: %d", evento.getMaxVagas()));
        vagas_restantes = new JLabel(String.format("Vagas disponíveis: %d", evento.getVagasDisponiveis()));
        avaliacao_media = new JLabel(String.format("Avaliação média: %.2f", evento.getMediaAvaliacoes()));

        presenca_button = new JButton();
        avaliar_button = new JButton("Avaliar Evento");
        denunciar_button = new JButton("Denunciar Evento");
        salvar_button = new JButton();
        compartilhar_button = new JButton("Compartilhar Evento");

        // determina ação inicial dos botões de marcar/desmarcar presença e salvar
        if (evento.confirmouPresenca(model.getLoggedUser().getCartao()))
        {
            presenca_button.setActionCommand("Desmarcar Presença");
            presenca_button.setText("Desmarcar Presença");
        }
        else
        {
            presenca_button.setActionCommand("Marcar Presença");
            presenca_button.setText("Marcar Presença");
        }

        if (model.getLoggedUser().eventoEstaSalvo(evento.getID()))
        {
            salvar_button.setActionCommand("Remover Evento Salvo");
            salvar_button.setText("Remover Evento Salvo");
        }
        else
        {
            salvar_button.setActionCommand("Salvar Evento");
            salvar_button.setText("Salvar Evento");
        }
        
        avaliacao_nota = new JSlider(0,10,5);
        avaliacao_nota.setMajorTickSpacing(1);
        avaliacao_nota.setSnapToTicks(true);
        avaliacao_nota.setPaintTicks(true);
        avaliacao_nota.setPaintLabels(true);

        presenca_button.addActionListener(this);
        avaliar_button.addActionListener(this);
        denunciar_button.addActionListener(this);
        salvar_button.addActionListener(this);
        compartilhar_button.addActionListener(this);

        descricao.setEditable(false);
        descricao.setColumns(20);
        descricao.setRows(5);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);

        frame.addWindowListener(this);

        patrocinios_table = new JTable(new UsuarioTableModel(new SponsorController(model).ListSponsors(evento),"Patrocinadores"));
        patrocinios_table.setAutoCreateRowSorter(true);
        JScrollPane patrocinios_scrollpane = new JScrollPane(patrocinios_table);
        patrocinios_scrollpane.setPreferredSize(new Dimension (280, 260));

        panel.setPreferredSize(new Dimension (640, 480));

        // formatação da UI
        JPanel new_line_1 = new JPanel();
        JPanel new_line_2 = new JPanel();
        JPanel new_line_3 = new JPanel();
        JPanel new_line_4 = new JPanel();
        JPanel new_line_5 = new JPanel();
        JPanel new_line_6 = new JPanel();
        JPanel new_line_7 = new JPanel();
        JPanel new_line_8 = new JPanel();
        JPanel new_line_9 = new JPanel();

        JPanel info_panel = new JPanel();
        info_panel.setPreferredSize(new Dimension (280, 260));

        new_line_1.setPreferredSize(new Dimension (640, 1));
        new_line_2.setPreferredSize(new Dimension (640, 1));
        new_line_3.setPreferredSize(new Dimension (640, 1));
        new_line_4.setPreferredSize(new Dimension (640, 1));
        new_line_5.setPreferredSize(new Dimension (640, 1));
        new_line_6.setPreferredSize(new Dimension (640, 1));
        new_line_7.setPreferredSize(new Dimension (640, 1));
        new_line_8.setPreferredSize(new Dimension (640, 1));
        new_line_9.setPreferredSize(new Dimension (640, 1));

        info_panel.add(nome_evento);
        info_panel.add(new_line_1);

        info_panel.add(nome_gerente);
        info_panel.add(new_line_2);

        info_panel.add(descricao);
        info_panel.add(new_line_3);

        info_panel.add(local);
        info_panel.add(new_line_4);

        info_panel.add(data);
        info_panel.add(horario);
        info_panel.add(new_line_5);

        info_panel.add(vagas_totais);
        info_panel.add(vagas_restantes);
        info_panel.add(new_line_6);

        info_panel.add(avaliacao_media);

        panel.add(info_panel);

        // só mostra patrocínios se existirem
        if (patrocinios_table.getModel().getRowCount() > 0)
            panel.add(patrocinios_scrollpane);

        panel.add(new_line_7);
        
        panel.add(presenca_button);
        panel.add(salvar_button);
        panel.add(new_line_8);

        panel.add(avaliacao_nota);
        panel.add(avaliar_button);
        panel.add(new_line_9);

        panel.add(denunciar_button);
        panel.add(compartilhar_button);

        Container pane = frame.getContentPane();

        pane.add(panel);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        Usuario logado = model.getLoggedUser();
        if (s.equals("Marcar Presença")){
            // está desmarcado, marcar
            if (!controller.MarcaPresenca(evento, logado)) // passou do limite de vagas
                JOptionPane.showMessageDialog(null, "Evento lotado! Pedido de presença salvo.");
            else
            {
                JOptionPane.showMessageDialog(null, "Presença marcada!");

                presenca_button.setActionCommand("Desmarcar Presença");
                presenca_button.setText("Desmarcar Presença");
            }
        }
        else if (s.equals("Desmarcar Presença")) {
            // está marcado, desmarcar
            controller.DesmarcaPresenca(evento, logado);
            JOptionPane.showMessageDialog(null, "Presença desmarcada!");

            presenca_button.setActionCommand("Marcar Presença");
            presenca_button.setText("Marcar Presença");
        }
        else if (s.equals("Avaliar Evento")) {
            
            int nota = avaliacao_nota.getValue(); 
            
            if (controller.AvaliaEvento(evento, logado, nota)) 
                JOptionPane.showMessageDialog(null, "Avaliação registrada!");
            else
                JOptionPane.showMessageDialog(null, "Você já avaliou esse evento!");
        }
        else if (s.equals("Denunciar Evento")) {
            
            String motivo;

            do
            {
                motivo = (String)JOptionPane.showInputDialog("Motivo da denúncia:");

                if (motivo == null) // usuário fechou a janela
                    return;

                if (motivo.equals(""))
                    JOptionPane.showMessageDialog(null, "Forneça um motivo!");
            }
            while(motivo.equals(""));
            
            if (controller.DenunciaEvento(evento, logado, motivo))
                JOptionPane.showMessageDialog(null, "Evento denunciado.");
            else
                JOptionPane.showMessageDialog(null, "Você já denunciou esse evento.");
        }
        else if (s.equals("Salvar Evento")) {
            
            controller.SalvaEvento(evento, logado);
            JOptionPane.showMessageDialog(null, "Evento salvo!");

            salvar_button.setActionCommand("Remover Evento Salvo");
            salvar_button.setText("Remover Evento Salvo");
        }
        else if (s.equals("Remover Evento Salvo")) {
            
            controller.RemoveEventoSalvo(evento, logado);
            JOptionPane.showMessageDialog(null, "Evento removido da lista de salvos!");

            salvar_button.setActionCommand("Salvar Evento");
            salvar_button.setText("Salvar Evento");
        }
        else if (s.equals("Compartilhar Evento")) {

            String myString = controller.CompartilhaEvento(evento);

            // manda a string para a área de transferência
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            JOptionPane.showMessageDialog(null, "Texto copiado para a área de transferência!");
        }
    }

    public void windowClosing(WindowEvent e) {
        model.updateEventoOnList(evento.getID(),evento);
        model.updateUsuarioOnList(model.getLoggedUser().getCartao(),model.getLoggedUser());
        model.saveEventos();
        model.saveUsuarios();
    }

    public void windowClosed(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
