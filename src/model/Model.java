package model;

import java.util.ArrayList;

import util.JsonManip;

public class Model {

    private ArrayList<Evento> evento_data;
    private ArrayList<Usuario> usuario_data;
    private Usuario logged_user;

    public Model()
    {
        evento_data = new ArrayList<Evento>();
        usuario_data = new ArrayList<Usuario>();

        logged_user = null;
        
        /*
        // placeholder: database hardcoded
        Evento e1 = new Evento(1, "Campeonato de PES",1, "DACOMP","Competição de PES no PS2, equipes nacionais", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,9), LocalTime.of(12,30), 8);
        Evento e2 = new Evento(2, "Campeonato de SF4",1, "DACOMP","Competição de SF4, estilo pro, Hugo banido", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,8), LocalTime.of(13,30), 16);
        Evento e3 = new Evento(3, "Campeonato de OMK",1, "DACOMP","*cackle**cackle**cackle*", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,10), LocalTime.of(13,30), 8);
        Evento e4 = new Evento(4, "Seminário de ES",2, "Prof. Galante", "Aprofundamento da matéria de ES", "Auditório Prof. Castilho", LocalDate.of(2023,8,1), LocalTime.of(15,30), 10);
        Evento e5 = new Evento(5, "Seminário de FBD",2, "Prof. Galante", "Aprofundamento da matéria de FBD", "Sala 112 Prédio 43425", LocalDate.of(2023,7,27), LocalTime.of(17,30), 15);
        Evento e6 = new Evento(6 ,"Seminário de SF4",1, "DACOMP", "Ensinamento dos básicos de SF4", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,3), LocalTime.of(10,30), 10);
        Evento e7 = new Evento(7, "Campeonato de Xadrez",3, "Prof. Nicolas", "Campeonato de xadrez entre os professores e alunos", "Sala de Convivência do DACOMP", LocalDate.of(2023,8,7), LocalTime.of(12,30), 4);

        evento_data.add(e1);
        evento_data.add(e2);
        evento_data.add(e3);
        evento_data.add(e4);
        evento_data.add(e5);
        evento_data.add(e6);
        evento_data.add(e7);
        
        JsonManip.saveArrayListToJson("Eventos.json", evento_data);
        */

        /*
        Usuario u1 = new Usuario(1, "DACOMP", "password", false);
        Usuario u2 = new Usuario(10, "João Oppenheimer", "lebomb", false);
        Usuario u3 = new Usuario(11, "Zezinho da Silva", "senha", false);
        Usuario u4 = new Usuario(999, "Ademir da Silva", "admin", true);
        Usuario u5 = new Usuario(5, "Sandra Pereira", "123456789", false);
        Usuario u6 = new Usuario(5, "claudio Gomes", "biruleibe", false);

        usuario_data.add(u1);
        usuario_data.add(u2);
        usuario_data.add(u3);
        usuario_data.add(u4);
        usuario_data.add(u5);
        usuario_data.add(u6);

        //JsonManip.saveArrayListToJson("Usuarios.json", usuario_data);
        */

        evento_data  = JsonManip.readArrayListFromJson("Eventos.json", Evento.class);
        usuario_data = JsonManip.readArrayListFromJson("Usuarios.json", Usuario.class);
    }

    public ArrayList<Evento> getEventoList()
    {
        return evento_data;
    }

    public ArrayList<Usuario> getUsuarioList()
    {
        return usuario_data;
    }

    public Usuario getLoggedUser()
    {
        return logged_user;
    }

    // se já existe evento com ID == eventID, atualiza dados
    public void updateEventoOnList(Integer eventID, Evento new_data)
    {
        for (int i = 0; i < evento_data.size(); i++)
            if (evento_data.get(i).getID() == eventID)
            {
                evento_data.set(i, new_data);
                return;
            }
    }

    public void addEventoToList(Evento new_evento)
    {
        evento_data.add(new_evento); // assume que não já existe evento com esse ID na lista
    }

    public void removeEventoFromList(Integer eventID)
    {
        for (int i = 0; i < evento_data.size(); i++)
            if (evento_data.get(i).getID() == eventID)
            {
                evento_data.remove(i);
                return;
            }
    }

    // checa se evento existe na lista
    // (para prevenir ações em eventos que acabaram de ser deletados)
    public boolean isEventoOnList(Integer eventID)
    {
        for (int i = 0; i < evento_data.size(); i++)
            if (evento_data.get(i).getID() == eventID)
                return true;
        return false;
    }

    // se já existe usuário com cartão == userID, atualiza dados
    public void updateUsuarioOnList(Integer userID, Usuario new_data)
    {
        for (int i = 0; i < usuario_data.size(); i++)
            if (usuario_data.get(i).getCartao() == userID)
            {
                usuario_data.set(i, new_data);
                return;
            }
    }

    public void addUsuarioToList(Usuario new_usuario)
    {
        usuario_data.add(new_usuario); // assume que não já existe usuário com esse ID na lista
    }

    public void removeUsuarioFromList(Integer userID)
    {
        for (int i = 0; i < usuario_data.size(); i++)
            if (usuario_data.get(i).getCartao() == userID)
            {
                usuario_data.remove(i);
                return;
            }
    }

    // checa se usuario existe na lista
    // (para prevenir ações em usuários que acabaram de ser deletados)
    public boolean isUsuarioOnList(Integer userID)
    {
        for (int i = 0; i < usuario_data.size(); i++)
            if (usuario_data.get(i).getCartao() == userID)
                return true;
        return false;
    }

    public void setLoggedUser(Usuario user)
    {
        logged_user = user;
    }

    public void logOff()
    {
        logged_user = null;
    }

    public void saveEventos()//salva lista de Eventos em Eventos.json
    {
        JsonManip.saveArrayListToJson("Eventos.json", evento_data);
    }
    
    public void saveUsuarios()//salva lista de usuarios em Usuarios.json
    {
        JsonManip.saveArrayListToJson("Usuarios.json", usuario_data);
    }
}
