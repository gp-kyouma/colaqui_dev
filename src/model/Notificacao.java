package model;

import java.time.LocalDate;

public class Notificacao {

    private String notificacao; 
    private LocalDate time;

    public Notificacao(String notificacao)
    {
        this.notificacao = notificacao;
        this.time = LocalDate.now();
    }

    public String getNotificacao()
    {
        return notificacao;
    }
    
    public LocalDate getTime()
    {
        return time;
    }
}
