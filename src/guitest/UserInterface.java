package guitest;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class UserInterface implements ActionListener {

    // MVC
    private Controller controller;
    
    // atributos referentes ao GUI
    private JFrame frame;

    private JPanel actions_panel;
    
    private JButton search_event_button;
    private JLabel wip_label;

    private JPanel empty_panel;

    private JPanel search_panel;

    private JTextField search_text_box;
    private JButton search_keyword_button;
    private JButton search_manager_button;
    private JTable search_table;

    public UserInterface() {
        
        controller = new Controller();
        
        frame = new JFrame ("ColAqui 0.1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //construct preComponents
        
        // inicializa tabela de pesquisa com lista vazia (não foi feita nenhuma pesquisa ainda)
        SearchEventTableModel model = new SearchEventTableModel(new ArrayList<Evento>());

        //construct components
        wip_label = new JLabel ("WIP: outras funcionalidades");
        search_event_button = new JButton ("Pesquisar Evento");

        search_text_box = new JTextField (50);
        search_keyword_button = new JButton ("Pesquisar por Sentença Chave");
        search_manager_button = new JButton ("Pesquisar por Gerente de Evento");
        search_table = new JTable(model);
    }

    public void showGUI () {

        // addActionListener to button
        search_event_button.addActionListener(this);
        search_keyword_button.addActionListener(this);
        search_manager_button.addActionListener(this);

        actions_panel = new JPanel();
        empty_panel = new JPanel();
        search_panel = new JPanel();
        
        //adjust size and set layout
        //frame.setPreferredSize (new Dimension (640, 480));
        actions_panel.setPreferredSize(new Dimension (200, 480));

        empty_panel.setPreferredSize(new Dimension (600, 480));
        search_panel.setPreferredSize(new Dimension (600, 480));

        JScrollPane search_table_scrollpane = new JScrollPane(search_table);

        search_table_scrollpane.setPreferredSize(new Dimension (580, 400));
        
        //add components
        actions_panel.add (search_event_button);
        actions_panel.add (wip_label);
        search_panel.add (search_text_box);
        search_panel.add (search_keyword_button);
        search_panel.add (search_manager_button);
        search_panel.add (search_table_scrollpane);

        Container pane = frame.getContentPane();

        pane.add(actions_panel,BorderLayout.LINE_START);
        pane.add(empty_panel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible (true);
        frame.setResizable(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if (s.equals("Pesquisar Evento")) {
            
            Container pane = frame.getContentPane();
            BorderLayout layout = (BorderLayout) pane.getLayout();
            JPanel center_panel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);

            if (!search_panel.isValid())
            {
                pane.remove(center_panel);
                pane.add(search_panel,BorderLayout.CENTER);
                pane.validate();
            }
        }
        else if (s.equals("Pesquisar por Sentença Chave")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = controller.searchEventByKeyword(search_key);

            SearchEventTableModel model = (SearchEventTableModel)search_table.getModel();
            model.setSearchResults(searchResults);
        }
        else if (s.equals("Pesquisar por Gerente de Evento")) {
            
            String search_key = search_text_box.getText();
            ArrayList<Evento> searchResults = controller.searchEventByManager(search_key);

            SearchEventTableModel model = (SearchEventTableModel)search_table.getModel();
            model.setSearchResults(searchResults);
        }
    }
}
