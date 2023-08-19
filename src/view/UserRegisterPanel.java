package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.UserRegisterController;
import model.Model;

public class UserRegisterPanel extends JPanel implements ActionListener {

    private MainWindow window;
    
    private UserRegisterController controller;
    
    private JTextField user_name_box;
    private JTextField user_cartao_box;
    private JTextField user_password_box;
    private JLabel user_name_label;
    private JLabel user_cartao_label;
    private JLabel user_password_label;
    private JButton confirm_button;

    public UserRegisterPanel(Model model, MainWindow window) {

        controller = new UserRegisterController(model);
        this.window = window;

        user_name_box = new JTextField (40);
        user_cartao_box = new JTextField (40);
        user_password_box = new JTextField (40);
        user_name_label = new JLabel ("Nome de Usuário");
        user_cartao_label = new JLabel ("Cartão de Usuário");
        user_password_label = new JLabel ("Senha de Usuário");
        confirm_button = new JButton ("Confirmar");

        confirm_button.addActionListener(this);

        setPreferredSize(new Dimension (600, 480));

        add(user_name_label);
        add(user_name_box);
        add(user_cartao_label);
        add(user_cartao_box);
        add(user_password_label);
        add(user_password_box);
        add(confirm_button);
    }

    public void actionPerformed(ActionEvent e)
    {
        String entered_name = user_name_box.getText();
        String entered_cartao = user_cartao_box.getText();
        String entered_password = user_password_box.getText();

        if (entered_name.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Nome vazio!");
            return;
        }
        if (entered_cartao.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Cartão vazio!");
            return;
        }
        if (entered_password.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Senha vazia!");
            return;
        }
        
        String resultado = controller.CadastraUsuario(entered_cartao, entered_name, entered_password);

        if (resultado.equals("cartao_errado"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Cartão deve conter somente números!");
            return;
        }
        if (resultado.equals("cartao_nao_unico"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Já existe usuário com esse cartão!");
            return;
        }
        if (resultado.equals("senha"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Senha deve conter somente letras (A-Z), números e underscore!");
            return;
        }
        if (resultado.equals("nome"))
        {
            JOptionPane.showMessageDialog(null, "ERRO: Nome deve conter somente letras e espaços!");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        user_name_box.setText("");
        user_cartao_box.setText("");
        user_password_box.setText("");
        window.updateCenterPanel("<Vazio>");
    }
}
