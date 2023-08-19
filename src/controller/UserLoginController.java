package controller;

import model.Model;
import model.Usuario;

public class UserLoginController {
    private Model model;

    public UserLoginController(Model model)
    {
        this.model = model;
    }

    public String validaCartao(String cartao, String senha)
    {
        // Ver se cartão só contém 0-9
        if (!cartao.matches("^[0-9]+$")) 
            return "cartao_errado";
        
        // Verificar se cartão existe
        Usuario encontrado = null;

        for (Usuario i : model.getUsuarioList())
            if (i.getCartao() == Integer.parseInt(cartao)) 
                encontrado = i;
        
        if (encontrado == null)
            return "cartao_inexistente";
        
        // Verificar se senha está correta
        if (!encontrado.getSenha().equals(senha))
            return "senha_incorreta";

        // Se tudo ok, faz login
        model.setLoggedUser(encontrado);
        return "ok";
    }
}
