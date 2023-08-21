package controller;

import java.util.ArrayList;

import model.Evento;
import model.Model;
import model.Usuario;

public class SponsorController {

    private Model model;

    private NotificationController notif_controller;

    public SponsorController(Model model)
    {
        this.model = model;
        notif_controller = new NotificationController(model);
    }

    public ArrayList<Evento> ListSponsoredEvents(Usuario user)
    {
        ArrayList<Evento> evento_data = model.getEventoList();

        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (i.isPatrocinador(user.getCartao()))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Usuario> ListSponsors(Evento evento)
    {
        ArrayList<Usuario> usuario_data = model.getUsuarioList();

        ArrayList<Usuario> results = new ArrayList<Usuario>();

        for (Usuario i : usuario_data)
        {
            if (evento.isPatrocinador(i.getCartao()))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Usuario> ListPotentialSponsors(Evento evento)
    {
        ArrayList<Usuario> usuario_data = model.getUsuarioList();

        ArrayList<Usuario> results = new ArrayList<Usuario>();

        for (Usuario i : usuario_data)
        {
            if (evento.fezProposta(i.getCartao()))
                results.add(i);
        }

        return results;
    }

    public String makeProposal(Evento evento, Usuario usuario, String proposta)
    {
        // adiciona à lista de propostas e manda notificação ao gerente

        if (usuario.isMeuEvento(evento.getID()))
            return "self_event";

        if (evento.fezProposta(usuario.getCartao()))
            return "already_proposed";

        if (evento.isPatrocinador(usuario.getCartao()))
            return "already_sponsoring";
        
        evento.recebePropostaPatrocinio(usuario.getCartao());
        model.updateEventoOnList(evento.getID(),evento);
        model.saveEventos();
        
        Usuario gerente = model.getUsuarioFromList(evento.getGerenteCartao());
        notif_controller.AddNotification(gerente, "Proposta de Patrocínio",
                                            "Proposta de Patrocínio:<br/>Patrocinador: "
                                            + usuario.getNome()
                                            + "<br/>Evento: " 
                                            + evento.getNome() 
                                            + "<br/>Proposta: "
                                            + proposta);

        return "ok";
    }

    public void acceptProposal(Evento evento, Usuario usuario)
    {
        // passa da lista de propostas pra lista de patrocinadores, e manda notificação ao patrocinador
        evento.aceitaPropostaPatrocinio(usuario.getCartao());

        notif_controller.AddNotification(usuario, "Proposta de patrocínio aceita","A sua proposta de patrocínio para o evento " + evento.getNome() + " foi aceita.");
    }

    public void rejectProposal(Evento evento, Usuario usuario)
    {
        // remove da lista de propostas, e manda notificação ao patrocinador
        evento.rejeitaPropostaPatrocinio(usuario.getCartao());

        notif_controller.AddNotification(usuario, "Proposta de patrocínio rejeitada","A sua proposta de patrocínio para o evento " + evento.getNome() + " foi rejeitada.");
    }
}
