package main;

import model.Model;
import view.MainWindow;

public class ColAqui {
    public static void main(String[] args) {

        Model model = new Model();
        MainWindow main_window = new MainWindow(model);
        main_window.show();
    
    }
}