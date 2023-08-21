package view;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import controller.SearchEventController;
import controller.SponsorController;
import model.Evento;
import model.Model;

public class SponsorProposalPanel extends JPanel implements ActionListener, MouseListener{

    private SearchEventController search_controller;
    private SponsorController controller;

    private Model model;
    
    private JTextField search_text_box;
    private JButton search_keyword_button;
    private JButton search_manager_button;
    private JTable search_table;

    private EventTableModel tableModel;

    public SponsorProposalPanel(Model model) {

        search_controller = new SearchEventController(model);
        controller = new SponsorController(model);
        this.model = model;

        // inicializa tabela de pesquisa com lista vazia (não foi feita nenhuma pesquisa ainda)
        tableModel = new EventTableModel(new ArrayList<Evento>(), false);

        search_text_box = new JTextField (40);
        search_keyword_button = new JButton ("Pesquisar por Sentença Chave");
        search_manager_button = new JButton ("Pesquisar por Gerente de Evento");
        search_table = new JTable(tableModel);

        search_table.setAutoCreateRowSorter(true);

        search_keyword_button.addActionListener(this);
        search_manager_button.addActionListener(this);

        search_table.addMouseListener(this);

        setPreferredSize(new Dimension (600, 480));

        JScrollPane search_table_scrollpane = new JScrollPane(search_table);

        search_table_scrollpane.setPreferredSize(new Dimension (580, 360));

        JLabel panel_special_info = new JLabel("Pesquisa especial de Patrocinador; automaticamente filtra inativos");

        add(panel_special_info);
        add(search_text_box);
        add(search_keyword_button);
        add(search_manager_button);
        add(search_table_scrollpane);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Pesquisar por Sentença Chave")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = search_controller.searchEventByKeyword(search_key);

            searchResults = SearchEventController.filterInactive(searchResults);

            tableModel.setListResults(searchResults);
        }
        else if (s.equals("Pesquisar por Gerente de Evento")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = search_controller.searchEventByManager(search_key);

            searchResults = SearchEventController.filterInactive(searchResults);

            tableModel.setListResults(searchResults);
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        Point point = mouseEvent.getPoint();
        int row = search_table.rowAtPoint(point);

        if (mouseEvent.getClickCount() == 2 && search_table.getSelectedRow() != -1) {
            Evento result = tableModel.getListResults().get(row);

            if (!model.isEventoOnList(result.getID()))
            {
                JOptionPane.showMessageDialog(null, "Evento inexistente!");
                return;
            }
            
            String proposta;
            do
            {
                proposta = (String)JOptionPane.showInputDialog("Faça a sua proposta:");

                if (proposta == null) // usuário fechou a janela
                    return;

                if (proposta.equals(""))
                    JOptionPane.showMessageDialog(null, "Você deve fazer uma proposta válida!");
            }
            while(proposta.equals(""));

            String proposalResult = controller.makeProposal(result, model.getLoggedUser(), proposta);
            
            if (proposalResult.equals("self_event"))
                JOptionPane.showMessageDialog(null, "Esse evento é seu!");
            else if (proposalResult.equals("already_proposed"))
                JOptionPane.showMessageDialog(null, "Você já fez uma proposta de patrocínio a esse evento.");
            else if (proposalResult.equals("already_sponsoring"))
                JOptionPane.showMessageDialog(null, "Você já está patrocinando esse evento.");
            else
                JOptionPane.showMessageDialog(null, "Proposta de patrocínio anunciada!");
        }
    }

    //unused
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
