package controller;

import model.Model;
import model.Usuario;

public class NotificationController {
    
    private Model model;

    public NotificationController(Model model)
    {
        this.model = model;
    }

    public void AddNotification(Usuario usuario, String header, String notificacao)
    {
        usuario.addNotificacao(header, notificacao);
        model.updateUsuarioOnList(usuario.getCartao(), usuario);
        model.saveUsuarios();
    }

    // Manda notificação a todos os admins
    public void AddNotificationAdmin(String header, String notificacao)
    {
        for (Usuario i : model.getUsuarioList())
            if(i.isAdmin())
            {
                i.addNotificacao(header, notificacao);
                model.updateUsuarioOnList(i.getCartao(), i);
            }
        model.saveUsuarios();
    }

    public void RemoveNotification(Usuario usuario, int indice)
    {
        usuario.removeNotificacao(indice);
        model.updateUsuarioOnList(usuario.getCartao(), usuario);
        model.saveUsuarios();
    }
}

