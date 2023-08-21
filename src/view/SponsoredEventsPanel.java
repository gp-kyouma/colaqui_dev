package view;

import java.util.ArrayList;

import java.awt.*;

import javax.swing.*;

import controller.SponsorController;
import model.Evento;
import model.Model;

public class SponsoredEventsPanel extends JPanel{
    
    private SponsorController controller;

    private Model model;
    
    private JTable sponsored_events_table;

    private EventTableModel tableModel;

    public SponsoredEventsPanel(Model model) {

        controller = new SponsorController(model);
        this.model = model;

        // inicializa tabela de listagem vazia
        tableModel = new EventTableModel(new ArrayList<Evento>(), false);

        sponsored_events_table = new JTable(tableModel);

        sponsored_events_table.setAutoCreateRowSorter(true);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane sponsored_events_table_scrollpane = new JScrollPane(sponsored_events_table);

        sponsored_events_table_scrollpane.setPreferredSize(new Dimension (580, 460));

        add(sponsored_events_table_scrollpane);
    }

    public void updateListing()
    {
        tableModel.setListResults(controller.ListSponsoredEvents(model.getLoggedUser()));
    }
}
