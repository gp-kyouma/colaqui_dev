package view;

import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

import java.awt.*;
import javax.swing.*;

import controller.NotificationController;
import model.Evento;
import model.Model;
import model.Usuario;

public class UserEventWindow extends SecondaryWindow implements ActionListener, WindowListener {
    
    private JPanel panel;

    private Evento evento;
    private Model model;

    private NotificationController controller;

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

    public UserEventWindow(Evento evento, Model model)
    {
        frame = new JFrame (evento.getNome());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.evento = evento;
        this.model = model;
        controller = new NotificationController(model);

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

        new_line_1.setPreferredSize(new Dimension (640, 1));
        new_line_2.setPreferredSize(new Dimension (640, 1));
        new_line_3.setPreferredSize(new Dimension (640, 1));
        new_line_4.setPreferredSize(new Dimension (640, 1));
        new_line_5.setPreferredSize(new Dimension (640, 1));
        new_line_6.setPreferredSize(new Dimension (640, 1));
        new_line_7.setPreferredSize(new Dimension (640, 1));
        new_line_8.setPreferredSize(new Dimension (640, 1));
        new_line_9.setPreferredSize(new Dimension (640, 1));

        panel.add(nome_evento);
        panel.add(new_line_1);

        panel.add(nome_gerente);
        panel.add(new_line_2);

        panel.add(descricao);
        panel.add(new_line_3);

        panel.add(local);
        panel.add(new_line_4);

        panel.add(data);
        panel.add(horario);
        panel.add(new_line_5);

        panel.add(vagas_totais);
        panel.add(vagas_restantes);
        panel.add(new_line_6);

        panel.add(avaliacao_media);
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
        if (s.equals("Marcar Presença"))
        {
            // está desmarcado, marcar
            boolean result = evento.addPresenca(logado.getCartao());
            if (!result) // passou do limite de vagas
                JOptionPane.showMessageDialog(null, "Evento lotado! Pedido de presença salvo.");
            else
            {
                logado.addPresenca(evento.getID());
                JOptionPane.showMessageDialog(null, "Presença marcada!");

                presenca_button.setActionCommand("Desmarcar Presença");
                presenca_button.setText("Desmarcar Presença");
            }
        }
        else if (s.equals("Desmarcar Presença")) {
            // está marcado, desmarcar
            evento.removePresenca(logado.getCartao());
            logado.removePresenca(evento.getID());
            JOptionPane.showMessageDialog(null, "Presença desmarcada!");

            presenca_button.setActionCommand("Marcar Presença");
            presenca_button.setText("Marcar Presença");
        }
        else if (s.equals("Avaliar Evento")) {
            int nota = avaliacao_nota.getValue(); 
            if (evento.addAvaliacao(logado.getCartao(), nota))
                JOptionPane.showMessageDialog(null, "Avaliação registrada!");
            else
                JOptionPane.showMessageDialog(null, "Você já avaliou esse evento!");
        }
        else if (s.equals("Denunciar Evento")) {
            
            String denunciaString;

            do
            {
                denunciaString = (String)JOptionPane.showInputDialog("Motivo da denúncia:");

                if (denunciaString == null) // usuário fechou a janela
                    return;

                if (denunciaString.equals(""))
                    JOptionPane.showMessageDialog(null, "Forneça um motivo!");
            }
            while(denunciaString.equals(""));
            
            if (evento.addDenuncia(logado.getCartao(),denunciaString))
            { 
                JOptionPane.showMessageDialog(null, "Evento denunciado.");
                if (evento.getNumDenuncias() == Evento.MUITAS_DENUNCIAS)
                    controller.AddNotificationAdmin("O evento " + evento.getNome() + " recebeu múltiplas denúncias.");
            }
            else
                JOptionPane.showMessageDialog(null, "Você já denunciou esse evento.");
        }
        else if (s.equals("Salvar Evento")) {
            logado.salvaEvento(evento.getID());
            JOptionPane.showMessageDialog(null, "Evento salvo!");

            salvar_button.setActionCommand("Remover Evento Salvo");
            salvar_button.setText("Remover Evento Salvo");
        }
        else if (s.equals("Remover Evento Salvo")) {
            logado.removeEventoSalvo(evento.getID());
            JOptionPane.showMessageDialog(null, "Evento removido da lista de salvos!");

            salvar_button.setActionCommand("Salvar Evento");
            salvar_button.setText("Salvar Evento");
        }
        else if (s.equals("Compartilhar Evento")) {
            DateTimeFormatter data_formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
            DateTimeFormatter hora_formatter = DateTimeFormatter.ofPattern("HH:mm");

            String myString = String.format("Estarei presente no evento %s, dia %s às %s, no local %s! Cortesia do Programa ColAqui!",  evento.getNome(), data_formatter.format(evento.getData()), hora_formatter.format(evento.getHorario()), evento.getLocal());
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
