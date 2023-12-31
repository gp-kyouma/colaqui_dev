package view;

import java.awt.event.*;
import java.time.format.DateTimeFormatter;

import java.awt.*;
import javax.swing.*;

import controller.AdminEventController;
import controller.ListController;
import controller.SponsorController;
import model.Evento;
import model.Model;

public class AdminEventWindow extends SecondaryWindow implements ActionListener, WindowListener, MouseListener {
    
    private JPanel panel;

    private Evento evento;
    private Model model;

    private AdminEventController controller;

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
    private JTable denuncias_table;

    private JTable propostas_table;
    private JTable patrocinios_table;

    private ReportTableModel denuncias_model;

    public AdminEventWindow(Evento evento, Model model)
    {
        frame = new JFrame (evento.getNome());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.evento = evento;
        this.model = model;
        controller = new AdminEventController(model);

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

        descricao.setEditable(false);
        descricao.setColumns(20);
        descricao.setRows(5);
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);

        denuncias_model = new ReportTableModel(evento.getDenuncias());

        presencas_table = new JTable(new UsuarioTableModel(new ListController(model).ListPresencas(evento),"Presenças"));
        denuncias_table = new JTable(denuncias_model);

        frame.addWindowListener(this);
        denuncias_table.addMouseListener(this);

        presencas_table.setAutoCreateRowSorter(true);
        JScrollPane presencas_scrollpane = new JScrollPane(presencas_table);
        presencas_scrollpane.setPreferredSize(new Dimension (300, 150));

        denuncias_table.setAutoCreateRowSorter(true);
        JScrollPane denuncias_scrollpane = new JScrollPane(denuncias_table);
        denuncias_scrollpane.setPreferredSize(new Dimension (300, 150));

        propostas_table = new JTable(new UsuarioTableModel(new SponsorController(model).ListPotentialSponsors(evento),"Propostas de Patrocínio"));
        patrocinios_table = new JTable(new UsuarioTableModel(new SponsorController(model).ListSponsors(evento),"Patrocinadores"));

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

        new_line_1.setPreferredSize(new Dimension (640, 1));
        new_line_2.setPreferredSize(new Dimension (640, 1));
        new_line_3.setPreferredSize(new Dimension (640, 1));
        new_line_4.setPreferredSize(new Dimension (640, 1));
        new_line_5.setPreferredSize(new Dimension (640, 1));
        new_line_6.setPreferredSize(new Dimension (640, 1));
        new_line_7.setPreferredSize(new Dimension (640, 1));

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
        panel.add(denuncias_scrollpane);

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
    }

    public void windowClosed(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = denuncias_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && denuncias_table.getSelectedRow() != -1) {
            String result = denuncias_model.getListDenuncias().get(row);

            int n = JOptionPane.showConfirmDialog(null,"Essa denúncia é válida?","Denúncia",JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.NO_OPTION)
            {
                controller.DesconsiderarDenuncia(evento, result);
                denuncias_model.setListDenuncias(evento.getDenuncias());
            }
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

