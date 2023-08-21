package controller;

import model.Evento;
import model.Model;
import model.Usuario;

public class RemoveEventController {
    
    private Model model;

    public RemoveEventController(Model model)
    {
        this.model = model;
    }

    public void RemoveEvent(Evento evento, boolean byAdmin)
    {
        NotificationController notif_controller = new NotificationController(model);
        
        // Testar se evento existe
        if (!model.isEventoOnList(evento.getID()))
            return;

        // Manda notificações aos usuários relevantes
        for (Usuario i : model.getUsuarioList())
        {
            if (i.confirmouPresenca(evento.getID()))
            {
                i.removePresenca(evento.getID());
                notif_controller.AddNotification(i, "Presença cancelada", "Sua presença no evento " + evento.getNome() + " foi cancelada pois ele foi excluído.");
            }
            if (i.eventoEstaSalvo(evento.getID()))
            {
                i.removeEventoSalvo(evento.getID());
                notif_controller.AddNotification(i, "Evento salvo excluído", "O evento " + evento.getNome() + " foi removido da lista de eventos salvos pois ele foi excluído.");
            }
            if (i.isMeuEvento(evento.getID()))
            {
                i.removeMeuEvento(evento.getID());
                if (byAdmin)
                    notif_controller.AddNotification(i, "Evento removido por administrador", "Seu evento " + evento.getNome() + " violou as normas da plataforma e foi retirado do ar.");
            }
            if (evento.fezProposta(i.getCartao()))
            {
                notif_controller.AddNotification(i, "Proposta de patrocínio cancelada", "Sua proposta de patrocínio do evento " + evento.getNome() + " foi cancelada pois ele foi excluído.");
            }
            if (evento.isPatrocinador(i.getCartao()))
            {
                notif_controller.AddNotification(i, "Patrocínio cancelado", "Seu patrocínio do evento " + evento.getNome() + " foi cancelado pois ele foi excluído.");
            }

            model.updateUsuarioOnList(i.getCartao(),i);
        }
        model.saveUsuarios();

        model.removeEventoFromList(evento.getID());
        model.saveEventos();
    }
}
