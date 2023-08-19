package controller;

import java.util.ArrayList;

import model.Model;
import model.Usuario;
import model.Evento;

public class ListEventController {

    private Model model;

    public ListEventController(Model model)
    {
        this.model = model;
    }

    public ArrayList<Evento> ListEvents(Usuario user)
    {
        ArrayList<Evento> evento_data = model.getEventoList();

        if (user.isAdmin())
            return evento_data;
            
        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (user.isMeuEvento(i.getID()))
                results.add(i);
        }

        return results;
    }
}
