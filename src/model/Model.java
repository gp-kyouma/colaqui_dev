package model;

import java.util.ArrayList;

import util.JsonManip;

public class Model {

    private ArrayList<Evento> evento_data;
    // usuarios etc etc

    public Model()
    {
        evento_data = new ArrayList<Evento>();
        
        /*
        // placeholder: database hardcoded
        Evento e1 = new Evento("Campeonato de PES", "DACOMP", "Sala de Convivência do DACOMP", "Toda quarta 12:30", 8);
        Evento e2 = new Evento("Campeonato de SF4", "DACOMP", "Sala de Convivência do DACOMP", "Toda terça 13:30", 16);
        Evento e3 = new Evento("Campeonato de OMK", "DACOMP", "Sala de Convivência do DACOMP", "Toda quinta 13:30", 8);
        Evento e4 = new Evento("Seminário de ES", "Prof. Galante", "Auditório Prof. Castilho", "01/08/23 15:30", 10);
        Evento e5 = new Evento("Seminário de FBD", "Prof. Galante", "Sala 112 Prédio 43425", "27/07/23 17:30", 15);
        Evento e6 = new Evento("Seminário de SF4", "DACOMP", "Sala de Convivência do DACOMP", "03/08/23 10:30", 10);
        Evento e7 = new Evento("Campeonato de Xadrez", "Prof. Nicolas", "Sala de Convivência do DACOMP", "Toda segunda 12:30", 4);

        evento_data.add(e1);
        evento_data.add(e2);
        evento_data.add(e3);
        evento_data.add(e4);
        evento_data.add(e5);
        evento_data.add(e6);
        evento_data.add(e7);
        */
        
        evento_data = JsonManip.readArrayListFromJson("Eventos.json", Evento.class);
    }

    public ArrayList<Evento> getEventoList()
    {
        return evento_data;
    }

    // todo: setters/updaters
    // todo: save-to-json functions
}
