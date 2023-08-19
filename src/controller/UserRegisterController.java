package controller;

import model.Model;
import model.Usuario;

public class UserRegisterController {
    
    private Model model;

    public UserRegisterController(Model model)
    {
        this.model = model;
    }

    public String CadastraUsuario(String cartao, String nome, String senha)
    {
        // Ver se cartão só contém 0-9
        if (!cartao.matches("^[0-9]+$")) 
            return "cartao_errado";
        
        // Ver se o cartão é único
        for (Usuario i : model.getUsuarioList())
            if (i.getCartao() == Integer.parseInt(cartao)) 
                return "cartao_nao_unico";
        
        // Ver se senha só contém a-z A-Z 0-9 _
        if (!senha.matches("^[a-zA-Z0-9_]+$")) 
            return "senha";
        
        // Ver se nome só contém a-z A-Z <espaço>
        if (!nome.matches("^[ A-Za-z]+$")) 
            return "nome";

        // Se tudo ok, insere na lista de usuários
        model.addUsuarioToList(new Usuario(Integer.parseInt(cartao), nome, senha, false));
        model.saveUsuarios();
        return "ok";
    }
}
