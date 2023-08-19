package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.UserLoginController;
import model.Model;

public class LoginPanel extends JPanel implements ActionListener {
    
    private MainWindow window;
    private Model model;

    private UserLoginController controller;
    
    private JButton login_button;
    private JButton user_register_button;

    private JTextField cartao_box;
    private JTextField password_box;
    
    private JLabel cartao_label;
    private JLabel password_label;

    public LoginPanel(Model model, MainWindow window)
    {
        this.window = window;
        this.model = model;

        controller = new UserLoginController(model);
        
        user_register_button = new JButton ("Cadastrar");
        login_button = new JButton("Fazer Login");

        cartao_box = new JTextField (10);
        password_box = new JTextField (10);
        cartao_label = new JLabel ("Cartão:");
        password_label = new JLabel ("Senha:");

        user_register_button.addActionListener(this);
        login_button.addActionListener(this);

        setPreferredSize(new Dimension (200, 480));

        JPanel empty_line = new JPanel(); // pra ficar em linhas diferentes os botões
        empty_line.setPreferredSize(new Dimension(200,0));

        add(cartao_label);
        add(cartao_box);
        add(password_label);
        add(password_box);
        add(login_button);
        add(empty_line);
        add(user_register_button);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();

        if (s.equals("Cadastrar")) {
            window.updateCenterPanel("Cadastrar");
        }
        else if (s.equals("Fazer Login")){
            String entered_cartao = cartao_box.getText();
            String entered_password = password_box.getText();

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

            String resultado = controller.doLogin(entered_cartao,entered_password);

            if (resultado.equals("cartao_errado"))
            {
                JOptionPane.showMessageDialog(null, "ERRO: Cartão deve conter somente números!");
                return;
            }
            if (resultado.equals("cartao_inexistente"))
            {
                JOptionPane.showMessageDialog(null, "ERRO: Usuário inexistente!");
                return;
            }
            if (resultado.equals("senha_incorreta"))
            {
                JOptionPane.showMessageDialog(null, "ERRO: Senha incorreta!");
                return;
            }

            cartao_box.setText("");
            password_box.setText("");
            window.updateCenterPanel("<Vazio>");

            if (model.getLoggedUser().isAdmin())
                window.updateSidePanel("ADMIN_PANEL");
            else
                window.updateSidePanel("USER_PANEL");
        }
    }
}

