package controller;

import java.util.ArrayList;

import model.Model;
import model.Evento;

public class SearchEventController {

    private Model model;

    public SearchEventController(Model model)
    {
        this.model = model;
    }

    public ArrayList<Evento> searchEventByKeyword(String key)
    {
        ArrayList<Evento> evento_data = model.getEventoList();
        
        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (i.getNome().contains(key))
                results.add(i);
        }

        return results;
    }

    public ArrayList<Evento> searchEventByManager(String key)
    {
        ArrayList<Evento> evento_data = model.getEventoList();
        
        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (i.getGerente().equals(key))
                results.add(i);
        }

        return results;
    }

    public static ArrayList<Evento> filterInactive(ArrayList<Evento> list)
    {
        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : list)
        {
            if (i.isAtivo())
                results.add(i);
        }

        return results;
    }
}
