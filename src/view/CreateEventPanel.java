package view;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;

import controller.CreateEventController;
import model.Model;

public class CreateEventPanel extends JPanel implements ActionListener {
    private MainWindow window;
    
    private CreateEventController controller;
    
    private JTextField event_name_box;
    private JTextArea event_desc_box;
    private JTextField event_local_box;
    private JSpinner event_date_spinner;
    private JSpinner event_time_spinner;
    private JSpinner event_vagas_spinner;
    private JLabel event_name_label;
    private JLabel event_desc_label;
    private JLabel event_local_label;
    private JLabel event_date_label;
    private JLabel event_time_label;
    private JLabel event_vagas_label;
    private JButton confirm_button;

    public CreateEventPanel(Model model, MainWindow window) {

        controller = new CreateEventController(model);
        this.window = window;

        event_name_box = new JTextField (40);
        event_desc_box = new JTextArea (2,40);
        event_local_box = new JTextField (40);
        
        event_date_spinner = new JSpinner(new SpinnerDateModel());
        event_time_spinner = new JSpinner(new SpinnerDateModel());
        event_vagas_spinner = new JSpinner(new SpinnerNumberModel(10,1,100,1));

        ((SpinnerDateModel)event_date_spinner.getModel()).setCalendarField(Calendar.DAY_OF_MONTH);
        ((SpinnerDateModel)event_time_spinner.getModel()).setCalendarField(Calendar.HOUR_OF_DAY);

        event_date_spinner.setEditor(new JSpinner.DateEditor(event_date_spinner, "dd/MM/yyyy"));
        event_time_spinner.setEditor(new JSpinner.DateEditor(event_time_spinner, "HH:mm"));

        event_name_label = new JLabel ("Nome do evento:");
        event_desc_label = new JLabel ("Descrição do evento:");
        event_local_label = new JLabel("Local do evento:");
        event_date_label = new JLabel("Data do evento:");
        event_time_label = new JLabel("Horário do evento:");
        event_vagas_label = new JLabel ("Numero máximo de vagas:");
        confirm_button = new JButton ("Confirmar");

        confirm_button.addActionListener(this);

        setPreferredSize(new Dimension (600, 480));

        // formatação
        JPanel new_line = new JPanel();
        new_line.setPreferredSize(new Dimension (600, 1));

        add(event_name_label);
        add(event_name_box);
        add(event_desc_label);
        add(event_desc_box);
        add(event_local_label);
        add(event_local_box);
        add(event_date_label);
        add(event_date_spinner);
        add(event_time_label);
        add(event_time_spinner);
        add(new_line);
        add(event_vagas_label);
        add(event_vagas_spinner);
        add(confirm_button);
    }

    public void actionPerformed(ActionEvent e)
    {
        String event_name = event_name_box.getText();
        String event_desc = event_desc_box.getText();
        String event_local = event_local_box.getText();
        Date event_date = ((SpinnerDateModel)event_date_spinner.getModel()).getDate();
        Date event_time = ((SpinnerDateModel)event_time_spinner.getModel()).getDate();
        int event_vagas = ((SpinnerNumberModel)event_vagas_spinner.getModel()).getNumber().intValue();

        if (event_name.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Nome vazio!");
            return;
        }
        if (event_desc.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Descrição vazia!");
            return;
        }
        if (event_local.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Local vazio!");
            return;
        }
        
        String resultado = controller.CriarEvento(event_name,event_desc,event_local,event_date,event_time,event_vagas);

        if (resultado.equals("data_invalida"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Não pode criar um evento no passado!");
            return;
        }
        if (resultado.equals("vagas_invalido"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Número de vagas deve ser positivo!");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Evento criado com sucesso!");
        event_name_box.setText("");
        event_desc_box.setText("");
        event_local_box.setText("");
        event_vagas_spinner.setValue(10);
        window.updateCenterPanel("<Vazio>");
    }
}
