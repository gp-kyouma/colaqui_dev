package view;

import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

import java.awt.*;
import javax.swing.*;

import model.Evento;
import model.Model;
import model.Usuario;

public class UserEventWindow implements ActionListener, WindowListener {
    private JFrame frame;

    private JPanel panel;

    private Evento evento;
    private Model model;

    private JLabel nome_evento;
    private JLabel nome_gerente;
    private JTextField descricao;
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

        panel = new JPanel();

        DateTimeFormatter data_formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        DateTimeFormatter hora_formatter = DateTimeFormatter.ofPattern("HH:mm");

        nome_evento = new JLabel (evento.getNome());
        nome_gerente = new JLabel (String.format("Gerente do evento: %s", evento.getGerente()));
        descricao = new JTextField (evento.getDescricao());
        local = new JLabel (String.format("Local: %s", evento.getLocal()));
        data = new JLabel (String.format("Data: %s", data_formatter.format(evento.getData())));
        horario = new JLabel(String.format("Horário: %s", hora_formatter.format(evento.getHorario())));
        vagas_totais = new JLabel(String.format("Vagas totais: %d", evento.getMaxVagas()));
        vagas_restantes = new JLabel(String.format("Vagas disponíveis: %d", evento.getVagasDisponiveis()));
        avaliacao_media = new JLabel(String.format("Avaliação média: %.2f", evento.getMediaAvaliacoes()));

        presenca_button = new JButton("Marcar/Desmarcar Presença");
        avaliar_button = new JButton("Avaliar Evento");
        denunciar_button = new JButton("Denunciar Evento");
        salvar_button = new JButton("Salvar Evento");
        compartilhar_button = new JButton("Compartilhar Evento");
        
        avaliacao_nota = new JSlider(0,10,5);

        presenca_button.addActionListener(this);
        avaliar_button.addActionListener(this);
        denunciar_button.addActionListener(this);
        salvar_button.addActionListener(this);
        compartilhar_button.addActionListener(this);

        descricao.setEditable(false);

        frame.addWindowListener(this);

        panel.add(nome_evento);
        panel.add(nome_gerente);
        panel.add(descricao);
        panel.add(local);
        panel.add(data);
        panel.add(horario);
        panel.add(vagas_totais);
        panel.add(vagas_restantes);
        panel.add(avaliacao_media);
        
        panel.add(presenca_button);
        panel.add(avaliar_button);
        panel.add(denunciar_button);
        panel.add(salvar_button);
        panel.add(compartilhar_button);

        panel.add(avaliacao_nota);

        panel.setPreferredSize(new Dimension (640, 480));
        //todo: fix UI formatting
    }

    public void show() {

        Container pane = frame.getContentPane();

        pane.add(panel);

        frame.pack();
        frame.setVisible (true);
        frame.setResizable(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        Usuario logado = model.getLoggedUser();
        if (s.equals("Marcar/Desmarcar Presença")) {
            if (evento.confirmouPresenca(logado.getCartao()))
            {
                // está marcado, desmarcar
                evento.removePresenca(logado.getCartao());
                logado.removePresenca(evento.getID());
                JOptionPane.showMessageDialog(null, "Presença desmarcada!");
            }
            else
            {
                // está desmarcado, marcar
                boolean result = evento.addPresenca(logado.getCartao());
                if (!result) // passou do limite
                    JOptionPane.showMessageDialog(null, "Evento lotado! Pedido de presença salvo.");
                else
                {
                    logado.addPresenca(evento.getID());
                    JOptionPane.showMessageDialog(null, "Presença marcada!");
                }
            }
        }
        else if (s.equals("Avaliar Evento")) {
            int nota = avaliacao_nota.getValue(); 
            if (evento.addAvaliacao(logado.getCartao(), nota))
                JOptionPane.showMessageDialog(null, "Avaliação registrada!");
            else
                JOptionPane.showMessageDialog(null, "Você já avaliou esse evento!");
        }
        else if (s.equals("Denunciar Evento")) {
            if (evento.addDenuncia(logado.getCartao()))
                JOptionPane.showMessageDialog(null, "Evento denunciado.");
            else
                JOptionPane.showMessageDialog(null, "Você já denunciou esse evento.");
        }
        else if (s.equals("Salvar Evento")) {
            if (logado.salvaEvento(evento.getID()))
                JOptionPane.showMessageDialog(null, "Evento salvo!");
            else
                JOptionPane.showMessageDialog(null, "Você já salvou esse evento!");
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
