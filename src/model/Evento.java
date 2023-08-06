package model;

// NOT FINAL

public class Evento {

    // todo: event ID
    private String nome; // aka display name
    private String gerente; // placeholder, probably will be an user ID
    private String local;
    private String horario; // placeholder, probably will be DateTime or however it is in Java
    // todo: split horario em data (LocalDate) e horario (LocalTime)
    // todo: list of presences (IDs?)

    private Integer maxVagas;
    private Integer vagasOcupadas;  // this will just be presencesList.length()

    public Evento(String nome, String gerente, String local, String horario, Integer maxVagas)
    {
        this.nome = nome;
        this.gerente = gerente;
        this.local = local;
        this.horario = horario;
        this.maxVagas = maxVagas;
        this.vagasOcupadas = 0;
    }

    //todo: setters

    public String getNome()
    {
        return nome;
    }

    public String getGerente()
    {
        return gerente;
    }

    public String getLocal()
    {
        return local;
    }

    public String getHorario()
    {
        return horario;
    }

    public Integer getMaxVagas()
    {
        return maxVagas;
    }

    public Integer getVagasOcupadas()
    {
        return vagasOcupadas;
    }

    public Integer getVagasDisponiveis()
    {
        return (maxVagas - vagasOcupadas);
    }
}
