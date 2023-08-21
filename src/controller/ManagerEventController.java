package controller;

// Controller para as operações que o gerente de evento pode executar sobre (seus) eventos
// (excluir, aceitar/rejeitar presença excedente, remover presença, aceitar/rejeitar patrocínio)

import model.Model;
import model.Usuario;

import model.Evento;

public class ManagerEventController {
    private Model model;
    private NotificationController notif_controller;
    private RemoveEventController remove_controller;
    private SponsorController sponsor_controller;

    public ManagerEventController(Model model)
    {
        this.model = model;
        notif_controller = new NotificationController(model);
        remove_controller = new RemoveEventController(model);
        sponsor_controller = new SponsorController(model);
    }

    public void ExcluirEvento(Evento evento)
    {
        remove_controller.RemoveEvent(evento, false);
    }

    public void RemovePresenca(Evento evento, Usuario usuario)
    {
        evento.removePresenca(usuario.getCartao());
        usuario.removePresenca(evento.getID());
        model.updateEventoOnList(evento.getID(),evento);
        model.updateUsuarioOnList(usuario.getCartao(),usuario);
        notif_controller.AddNotification(usuario, "Sua presença no evento " + evento.getNome() + " foi cancelada pelo gerente.");
    }

    public void AdicionaPresencaExcedente(Evento evento, Usuario usuario)
    {
        evento.addPresencaExcedente(usuario.getCartao());
        usuario.addPresenca(evento.getID());
        model.updateEventoOnList(evento.getID(),evento);
        model.updateUsuarioOnList(usuario.getCartao(),usuario);
        notif_controller.AddNotification(usuario, "Seu pedido de presença excedente no evento " + evento.getNome() + " foi aceito.");
    }

    public void RejeitaPresencaExcedente(Evento evento, Usuario usuario)
    {
        evento.removePresencaExcedente(usuario.getCartao());
        model.updateEventoOnList(evento.getID(),evento);
        notif_controller.AddNotification(usuario, "Seu pedido de presença excedente no evento " + evento.getNome() + " foi rejeitado.");
    }

    public void AceitarPropostaPatrocinio(Evento evento, Usuario usuario)
    {
        sponsor_controller.acceptProposal(evento,usuario);
    }

    public void RejeitarPropostaPatrocinio(Evento evento, Usuario usuario)
    {
        sponsor_controller.rejectProposal(evento,usuario);
    }
}
