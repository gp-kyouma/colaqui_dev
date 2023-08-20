package controller;

import java.util.ArrayList;

import model.Model;
import model.Usuario;
import model.Evento;

public class ListController {

    private Model model;

    public ListController(Model model)
    {
        this.model = model;
    }

    public ArrayList<Evento> ListEvents(Usuario user)
    {
        ArrayList<Evento> evento_data = model.getEventoList();

        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (user.isAdmin() || user.isMeuEvento(i.getID()))  // admin lista todos, gerente lista os dele
                results.add(i);
        }

        return results;
    }

    public ArrayList<Evento> ListSavedEvents(Usuario user)
    {
        ArrayList<Evento> evento_data = model.getEventoList();

        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (user.eventoEstaSalvo(i.getID()))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Evento> ListConfirmedEvents(Usuario user)
    {
        ArrayList<Evento> evento_data = model.getEventoList();

        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (user.confirmouPresenca(i.getID()))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Usuario> ListPresencas(Evento evento)
    {
        ArrayList<Usuario> usuario_data = model.getUsuarioList();

        ArrayList<Usuario> results = new ArrayList<Usuario>();

        for (Usuario i : usuario_data)
        {
            if (evento.confirmouPresenca(i.getCartao()))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Usuario> ListPresencasExcedentes(Evento evento)
    {
        ArrayList<Usuario> usuario_data = model.getUsuarioList();

        ArrayList<Usuario> results = new ArrayList<Usuario>();

        for (Usuario i : usuario_data)
        {
            if (evento.solicitouPresencaExcedente(i.getCartao()))
                results.add(i);
        }

        return results;
    }
}
