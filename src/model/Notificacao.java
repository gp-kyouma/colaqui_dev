package model;

import java.time.LocalDate;

public class Notificacao {

    private String header;
    private String notificacao; 
    private LocalDate time;

    public Notificacao(String header, String notificacao)
    {
        this.header = header;
        this.notificacao = notificacao;
        this.time = LocalDate.now();
    }

    public String getHeader()
    {
        return header;
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
