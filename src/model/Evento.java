package model;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

// NOT FINAL

public class Evento {

    private Integer id;
    private String nome;        // aka display name do evento
    private String gerenteAcct; // account name do gerente
    private String gerenteNome; // display name do gerente
    private String local;
    private LocalDate data;
    private LocalTime horario;
    
    private ArrayList<String> presencasConfirmadas;

    private Integer maxVagas;

    public Evento(Integer id, String nome, String gerenteAcct, String gerenteNome, String local, LocalDate data, LocalTime horario, Integer maxVagas)
    {
        this.id = id;
        this.nome = nome;
        this.gerenteAcct = gerenteAcct;
        this.gerenteNome = gerenteNome;
        this.local = local;
        this.data = data;
        this.horario = horario;
        this.maxVagas = maxVagas;
        this.presencasConfirmadas = new ArrayList<String>();
    }

    //todo: setters

    public Integer getID()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public String getGerenteAcct()
    {
        return gerenteAcct;
    }

    public String getGerente()
    {
        return gerenteNome;
    }

    public String getLocal()
    {
        return local;
    }

    public LocalDate getData()
    {
        return data;
    }

    public LocalTime getHorario()
    {
        return horario;
    }

    public Integer getMaxVagas()
    {
        return maxVagas;
    }

    public Integer getVagasOcupadas()
    {
        return presencasConfirmadas.size();
    }

    public Integer getVagasDisponiveis()
    {
        return (maxVagas - presencasConfirmadas.size());
    }

    //todo: add presença, remove presença, etc.
}
