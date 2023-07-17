package guitest;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Controller {

    // model
    private ArrayList<Evento> evento_data;
    // etc etc

    public Controller()
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

        Gson gson = new Gson();
        
        String filename= "Eventos.json";
        File fr = new File(filename);
        String output = TextReader.ReadFileToText(fr);
        
        evento_data = gson.fromJson(output,TypeToken.getParameterized(ArrayList.class, Evento.class).getType());
    }

    public ArrayList<Evento> searchEventByKeyword(String key)
    {
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
        ArrayList<Evento> results = new ArrayList<Evento>();

        for (Evento i : evento_data)
        {
            if (i.getGerente().equals(key))
                results.add(i);
        }

        return results;
    }

}
