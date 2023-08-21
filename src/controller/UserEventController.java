package controller;

// Controller para as operações que o membro acadêmico pode executar sobre eventos
// (marcar/desmarcar presença, salvar, compartilhar, etc)

import model.Model;
import model.Usuario;

import java.time.format.DateTimeFormatter;

import model.Evento;

public class UserEventController {
    private Model model;
    private NotificationController notif_controller;

    public UserEventController(Model model)
    {
        this.model = model;
        notif_controller = new NotificationController(model);
    }

    public boolean MarcaPresenca(Evento evento, Usuario usuario)
    {
        if (!evento.addPresenca(usuario.getCartao())) // passou do limite de vagas
        {
            // notifica gerente do evento
            Usuario gerente = model.getUsuarioFromList(evento.getGerenteCartao());
            notif_controller.AddNotification(gerente, "Solicitação de presença excedente", "O usuário " + usuario.getNome() + " requisitou presença excedente no seu evento " + evento.getNome() + ".");
            return false;
        }

        usuario.addPresenca(evento.getID());
        return true;
    }

    public void DesmarcaPresenca(Evento evento, Usuario usuario)
    {
        evento.removePresenca(usuario.getCartao());
        usuario.removePresenca(evento.getID());
    }

    public boolean AvaliaEvento(Evento evento, Usuario usuario, int nota)
    {
        return evento.addAvaliacao(usuario.getCartao(), nota);
    }

    public boolean DenunciaEvento(Evento evento, Usuario usuario, String motivo)
    {
        if (!evento.addDenuncia(usuario.getCartao(), motivo))
            return false;
        
        if (evento.getNumDenuncias() == Evento.MUITAS_DENUNCIAS)
            notif_controller.AddNotificationAdmin("Evento recebeu muitas denúncias", "O evento " + evento.getNome() + " recebeu múltiplas denúncias.");
        
        return true;
    }

    public void SalvaEvento(Evento evento, Usuario usuario)
    {
        usuario.salvaEvento(evento.getID());
    }

    public void RemoveEventoSalvo(Evento evento, Usuario usuario)
    {
        usuario.removeEventoSalvo(evento.getID());
    }

    public String CompartilhaEvento(Evento evento)
    {
        DateTimeFormatter data_formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy");
        DateTimeFormatter hora_formatter = DateTimeFormatter.ofPattern("HH:mm");

        String myString = String.format("Estarei presente no evento %s, dia %s às %s, no local %s! Cortesia do Programa ColAqui!",  evento.getNome(), data_formatter.format(evento.getData()), hora_formatter.format(evento.getHorario()), evento.getLocal());

        return myString;
    }
}
