package view;

import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import java.awt.*;
import javax.swing.*;

import controller.ListController;
import controller.ManagerEventController;
import controller.SponsorController;
import model.Evento;
import model.Model;
import model.Usuario;

public class ManagerEventWindow extends SecondaryWindow implements ActionListener, MouseListener, WindowListener {
    
    private JPanel panel;

    private Evento evento;
    private Model model;

    private ManagerEventController controller;
    private ListController list_controller;
    private SponsorController sponsor_controller;

    private JLabel nome_evento;
    private JLabel nome_gerente;
    private JTextArea descricao;
    private JLabel local;
    private JLabel data;
    private JLabel horario;
    private JLabel vagas_totais;
    private JLabel vagas_restantes;
    private JLabel avaliacao_media;

    private JButton excluir_button;

    private JTable presencas_table;
    private JTable excedentes_table;

    private JTable propostas_table;
    private JTable patrocinios_table;

    public ManagerEventWindow(Evento evento, Model model)
    {
        frame = new JFrame (evento.getNome());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.evento = evento;
        this.model = model;
        controller = new ManagerEventController(model); 
        list_controller = new ListController(model);
        sponsor_controller = new SponsorController(model);

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

        excluir_button = new JButton("Excluir Evento");
        excluir_button.addActionListener(this);

        presencas_table  = new JTable(new UsuarioTableModel(list_controller.ListPresencas(evento),"Presenças"));
        excedentes_table = new JTable(new UsuarioTableModel(list_controller.ListPresencasExcedentes(evento),"Presenças Excedentes"));

        descricao.setEditable(false);
        descricao.setColumns(20);
        descricao.setRows(5);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);

        frame.addWindowListener(this);

        presencas_table.addMouseListener(this);
        excedentes_table.addMouseListener(this);

        presencas_table.setAutoCreateRowSorter(true);
        JScrollPane presencas_scrollpane = new JScrollPane(presencas_table);
        presencas_scrollpane.setPreferredSize(new Dimension (300, 150));

        excedentes_table.setAutoCreateRowSorter(true);
        JScrollPane excedentes_scrollpane = new JScrollPane(excedentes_table);
        excedentes_scrollpane.setPreferredSize(new Dimension (300, 150));

        propostas_table = new JTable(new UsuarioTableModel(sponsor_controller.ListPotentialSponsors(evento),"Propostas de Patrocínio"));
        patrocinios_table = new JTable(new UsuarioTableModel(sponsor_controller.ListSponsors(evento),"Patrocinadores"));

        propostas_table.addMouseListener(this);

        propostas_table.setAutoCreateRowSorter(true);
        JScrollPane propostas_scrollpane = new JScrollPane(propostas_table);
        propostas_scrollpane.setPreferredSize(new Dimension (300, 150));

        patrocinios_table.setAutoCreateRowSorter(true);
        JScrollPane patrocinios_scrollpane = new JScrollPane(patrocinios_table);
        patrocinios_scrollpane.setPreferredSize(new Dimension (300, 150));

        panel.setPreferredSize(new Dimension (640, 640));

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
        
        panel.add(presencas_scrollpane);
        panel.add(excedentes_scrollpane);

        panel.add(propostas_scrollpane);
        panel.add(patrocinios_scrollpane);

        panel.add(excluir_button);

        Container pane = frame.getContentPane();

        pane.add(panel);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Excluir Evento"))
        {
            int n = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir esse evento?","Excluir Evento",JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION)
            {
                controller.ExcluirEvento(evento);
                close();
            }
        }
    }

    public void windowClosing(WindowEvent e) {
        model.updateEventoOnList(evento.getID(),evento);
        model.saveEventos();
        model.saveUsuarios();
    }

    public void windowClosed(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public void mousePressed(MouseEvent mouseEvent) {
        JTable table = (JTable) mouseEvent.getSource();
        Point point = mouseEvent.getPoint();
        int row = table.rowAtPoint(point);
        UsuarioTableModel usuarioTableModel = (UsuarioTableModel)table.getModel();

        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
            String tipo = table.getModel().getColumnName(0);
            if (tipo.equals("Presenças"))
            {
                Usuario presenca = usuarioTableModel.getListUsuarios().get(row);
                int n = JOptionPane.showConfirmDialog(null,"Remover presença deste usuário?","Remover Presença de Usuário",JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION)
                {
                    controller.RemovePresenca(evento, presenca);
                    usuarioTableModel.setListUsuarios(list_controller.ListPresencas(evento));
                }
            }
            else if (tipo.equals("Presenças Excedentes"))
            {
                Usuario presenca = usuarioTableModel.getListUsuarios().get(row);
                int n = JOptionPane.showConfirmDialog(null,"Aceitar presença excedente desse usuário?","Aceitar Presença Excedente",JOptionPane.YES_NO_CANCEL_OPTION);
                if (n == JOptionPane.YES_OPTION)
                {
                    controller.AdicionaPresencaExcedente(evento, presenca);
                    usuarioTableModel.setListUsuarios(list_controller.ListPresencasExcedentes(evento));
                    ((UsuarioTableModel)presencas_table.getModel()).setListUsuarios(list_controller.ListPresencas(evento));
                }
                else if (n == JOptionPane.NO_OPTION)
                {
                    controller.RejeitaPresencaExcedente(evento, presenca);
                    usuarioTableModel.setListUsuarios(list_controller.ListPresencasExcedentes(evento));
                }
            }
            else if (tipo.equals("Propostas de Patrocínio"))
            {
                Usuario patrocinador = usuarioTableModel.getListUsuarios().get(row);
                int n = JOptionPane.showConfirmDialog(null,"Aceitar proposta de patrocínio?","Aceitar Patrocínio",JOptionPane.YES_NO_CANCEL_OPTION);
                if (n == JOptionPane.YES_OPTION)
                {
                    controller.AceitarPropostaPatrocinio(evento, patrocinador);
                    usuarioTableModel.setListUsuarios(sponsor_controller.ListPotentialSponsors(evento));
                    ((UsuarioTableModel)patrocinios_table.getModel()).setListUsuarios(sponsor_controller.ListSponsors(evento));
                }
                else if (n == JOptionPane.NO_OPTION)
                {
                    controller.RejeitarPropostaPatrocinio(evento, patrocinador);
                    usuarioTableModel.setListUsuarios(sponsor_controller.ListPotentialSponsors(evento));
                }
            }
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
