package guitest;

// BIG PLACEHOLDER LOL
// idk if this is what we'll use, but for search purposes it's good enough

public class Evento {

    private String nome;
    private String gerente; // placeholder, probably will be an ID
    private String local;
    private String horario; // placeholder, probably will be DateTime or however it is in Java

    private Integer maxVagas;
    private Integer vagasOcupadas;

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
