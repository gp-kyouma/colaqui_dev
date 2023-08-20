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
            if (evento_data.get(i).getID().equals(eventID))
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
            if (evento_data.get(i).getID().equals(eventID))
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
            if (evento_data.get(i).getID().equals(eventID))
                return true;
        return false;
    }

    public Evento getEventoFromList(Integer eventID)
    {
        for (int i = 0; i < evento_data.size(); i++)
            if (evento_data.get(i).getID().equals(eventID))
                return evento_data.get(i);
        return null;
    }

    // se já existe usuário com cartão == userID, atualiza dados
    public void updateUsuarioOnList(Integer userID, Usuario new_data)
    {
        for (int i = 0; i < usuario_data.size(); i++)
            if (usuario_data.get(i).getCartao().equals(userID))
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
            if (usuario_data.get(i).getCartao().equals(userID))
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
            if (usuario_data.get(i).getCartao().equals(userID))
                return true;
        return false;
    }

    public Usuario getUsuarioFromList(Integer userID)
    {
        for (int i = 0; i < usuario_data.size(); i++)
            if (usuario_data.get(i).getCartao().equals(userID))
                return usuario_data.get(i);
        return null;
    }

    public void setLoggedUser(Usuario user)
    {
        logged_user = user;
    }

    public void logout()
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
