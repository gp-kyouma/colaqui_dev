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
        Evento e1 = new Evento(1, "Campeonato de PES","dacomp", "DACOMP", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,9), LocalTime.of(12,30), 8);
        Evento e2 = new Evento(2, "Campeonato de SF4","dacomp", "DACOMP", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,8), LocalTime.of(13,30), 16);
        Evento e3 = new Evento(3, "Campeonato de OMK","dacomp", "DACOMP", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,10), LocalTime.of(13,30), 8);
        Evento e4 = new Evento(4, "Seminário de ES","galante", "Prof. Galante", "Auditório Prof. Castilho", LocalDate.of(2023,8,1), LocalTime.of(15,30), 10);
        Evento e5 = new Evento(5, "Seminário de FBD","galante", "Prof. Galante", "Sala 112 Prédio 43425", LocalDate.of(2023,7,27), LocalTime.of(17,30), 15);
        Evento e6 = new Evento(6 ,"Seminário de SF4","dacomp", "DACOMP", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,3), LocalTime.of(10,30), 10);
        Evento e7 = new Evento(7, "Campeonato de Xadrez","nicolas", "Prof. Nicolas", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,7), LocalTime.of(12,30), 4);

        evento_data.add(e1);
        evento_data.add(e2);
        evento_data.add(e3);
        evento_data.add(e4);
        evento_data.add(e5);
        evento_data.add(e6);
        evento_data.add(e7);
        */
        
        //JsonManip.saveArrayListToJson("Eventos.json", evento_data);

        evento_data = JsonManip.readArrayListFromJson("Eventos.json", Evento.class);
    }

    public ArrayList<Evento> getEventoList()
    {
        return evento_data;
    }

    // todo: setters/updaters
    // todo: save-to-json functions
}
