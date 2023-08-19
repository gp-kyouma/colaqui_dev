package model;

import java.util.ArrayList;

public class Usuario {

    private Integer cartao;
    private String nome;
    private String senha;
    private boolean isAdmin;
    
    // Lista de IDs de eventos criados pelo usuário
    private ArrayList<Integer> meusEventos;

    // Lista de IDs de eventos onde o usuário confirmou presença
    private ArrayList<Integer> eventosConfirmados;

    // Lista de IDs de eventos salvos pelo usuário
    private ArrayList<Integer> eventosSalvos;

    // Lista de notificações
    private ArrayList<Notificacao> notificacoes;

    public Usuario(Integer cartao, String nome, String senha, boolean isAdmin)
    {
        this.cartao = cartao;
        this.nome = nome;
        this.senha = senha;
        this.isAdmin = isAdmin;
        this.meusEventos = new ArrayList<Integer>();
        this.eventosConfirmados = new ArrayList<Integer>();
        this.eventosSalvos = new ArrayList<Integer>();
        this.notificacoes = new ArrayList<Notificacao>();
    }

    public Integer getCartao()
    {
        return cartao;
    }

    public String getNome()
    {
        return nome;
    }

    public String getSenha()
    {
        return senha;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public ArrayList<Integer> getMeusEventos()
    {
        return meusEventos;
    }
    
    public ArrayList<Integer> getEventosConfirmados()
    {
        return eventosConfirmados;
    }

    public ArrayList<Integer> getEventosSalvos()
    {
        return eventosSalvos;
    }

    public ArrayList<Notificacao> getNotificacoes()
    {
        return notificacoes;
    }

    public void addNotificacao(String notificacao)
    {
        notificacoes.add(new Notificacao(notificacao)); //Datetime definido no construtor
    }
    
    public void removeNotificacao(int indice)
    {
        notificacoes.remove(indice);
    }

    public boolean confirmouPresenca(Integer eventID)
    {
        return eventosConfirmados.contains(eventID);
    }

    // retorna true se conseguiu adicionar presença com sucesso,
    // senão retorna false
    public boolean addPresenca(Integer eventID)
    {
        if (confirmouPresenca(eventID))
            return false;   // não adiciona o mesmo evento múltiplas vezes
        
        eventosConfirmados.add(eventID);
        return true;
    }

    public void removePresenca(Integer eventID)
    {
        eventosConfirmados.remove(eventID);
    }

    public boolean isMeuEvento(Integer eventID)
    {
        return meusEventos.contains(eventID);
    }

    // retorna true se conseguiu adicionar evento com sucesso,
    // senão retorna false
    public boolean addMeuEvento(Integer eventID)
    {
        if (isMeuEvento(eventID))
            return false;   // não adiciona o mesmo evento múltiplas vezes
        
        meusEventos.add(eventID);
        return true;
    }

    public void removeMeuEvento(Integer userID)
    {
        meusEventos.remove(userID);
    }

    public boolean eventoEstaSalvo(Integer eventID)
    {
        return eventosSalvos.contains(eventID);
    }

    // retorna true se conseguiu adicionar evento com sucesso,
    // senão retorna false
    public boolean salvaEvento(Integer eventID)
    {
        if (eventoEstaSalvo(eventID))
            return false;   // não adiciona o mesmo evento múltiplas vezes
        
        eventosSalvos.add(eventID);
        return true;
    }

    public void removeEventoSalvo(Integer userID)
    {
        eventosSalvos.remove(userID);
    }
}
