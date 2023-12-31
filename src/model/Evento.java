package model;

import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Evento {

    public static int MUITAS_DENUNCIAS = 3;
    
    private Integer id;
    private String nome;        // aka display name do evento
    private Integer gerenteCartao; // cartão do gerente
    private String gerenteNome; // display name do gerente
    private String descricao;
    private String local;
    private LocalDate data;
    private LocalTime horario;
    
    private ArrayList<Integer> presencasConfirmadas;
    private ArrayList<Integer> presencasExcedentes;

    private Integer maxVagas;

    private ArrayList<Integer> avaliaram; // pessoas que já avaliaram esse evento
    private Integer numAvaliacoes;
    private Integer totalAvaliacoes;

    private ArrayList<Integer> denunciaram; // pessoas que já denunciaram esse evento
    private ArrayList<String> denuncias;

    private ArrayList<Integer> propostasPatrocinio; // lista de patrocinadores que fizeram propostas a esse evento
    private ArrayList<Integer> patrocinadores;      // lista de patrocinadores aceitos pelo gerente

    public Evento(Integer id, String nome, Integer gerenteCartao, String gerenteNome, String descricao, String local, LocalDate data, LocalTime horario, Integer maxVagas)
    {
        this.id = id;
        this.nome = nome;
        this.gerenteCartao = gerenteCartao;
        this.gerenteNome = gerenteNome;
        this.descricao = descricao;
        this.local = local;
        this.data = data;
        this.horario = horario;
        this.maxVagas = maxVagas;
        this.presencasConfirmadas = new ArrayList<Integer>();
        this.presencasExcedentes = new ArrayList<Integer>();
        this.avaliaram = new ArrayList<Integer>();
        this.numAvaliacoes = 0;
        this.totalAvaliacoes = 0;
        this.denunciaram = new ArrayList<Integer>();
        this.denuncias = new ArrayList<String>();
        this.propostasPatrocinio = new ArrayList<Integer>();
        this.patrocinadores = new ArrayList<Integer>();
    }

    public Integer getID()
    {
        return id;
    }

    public String getNome()
    {
        return nome;
    }

    public Integer getGerenteCartao()
    {
        return gerenteCartao;
    }

    public String getGerente()
    {
        return gerenteNome;
    }

    public String getDescricao()
    {
        return descricao;
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

    public float getMediaAvaliacoes()
    {
        if (numAvaliacoes == 0)
            return 0;
        
        return (totalAvaliacoes/numAvaliacoes);
    }

    public Integer getNumDenuncias()
    {
        return denuncias.size();
    }

    public ArrayList<String> getDenuncias()
    {
        return denuncias;
    }

    // retorna se o evento não ocorreu ainda (está no futuro)
    public boolean isAtivo()
    {
        return LocalDateTime.of(getData(), getHorario()).isAfter(LocalDateTime.now());
    }

    public ArrayList<Integer> getPresencasConfirmadas()
    {
        return presencasConfirmadas;
    }
    
    public ArrayList<Integer> getPresencasExcedentes()
    {
        return presencasExcedentes;
    }

    public boolean confirmouPresenca(Integer userID)
    {
        return presencasConfirmadas.contains(userID);
    }

    public boolean solicitouPresencaExcedente(Integer userID)
    {
        return presencasExcedentes.contains(userID);
    }

    // retorna true se conseguiu adicionar presença com sucesso,
    // senão retorna false
    public boolean addPresenca(Integer userID)
    {
        if (confirmouPresenca(userID))
            return false;   // não adiciona o mesmo membro múltiplas vezes
        
        // se não tem vagas, adiciona na lista de excedentes se já não está
        if (presencasConfirmadas.size() >= maxVagas)
        {
            if (!solicitouPresencaExcedente(userID))
                presencasExcedentes.add(userID);
            return false;
        }

        // se tem vagas e estava na lista de excedentes, tira de lá e bota na lista de presenças
        if (solicitouPresencaExcedente(userID))
        {
            addPresencaExcedente(userID);
            return true;
        }

        presencasConfirmadas.add(userID);
        return true;
    }

    public void removePresenca(Integer userID)
    {
        presencasConfirmadas.remove(userID);
    }

    public void removePresencaExcedente(Integer userID)
    {
        presencasExcedentes.remove(userID);
    }

    public void addPresencaExcedente(Integer userID)
    {
        presencasExcedentes.remove(userID);
        presencasConfirmadas.add(userID);
    }

    // usado somente em excluir usuário
    public boolean avaliou(Integer userID)
    {
        return avaliaram.contains(userID);
    }

    // retorna true se conseguiu adicionar avaliação com sucesso,
    // senão retorna false
    public boolean addAvaliacao(Integer userID, Integer nota)
    {
        if (avaliou(userID))
            return false;   // mesmo usuário não deve poder avaliar múltiplas vezes
        
        avaliaram.add(userID);
        numAvaliacoes++;
        totalAvaliacoes += nota;
        return true;
    }

    // usado somente em excluir usuário
    public void removeAvaliou(Integer userID)
    {
        avaliaram.remove(userID);
    }

    // usado somente em excluir usuário
    public boolean denunciou(Integer userID)
    {
        return denunciaram.contains(userID);
    }

    // retorna true se conseguiu adicionar denúncia com sucesso,
    // senão retorna false
    public boolean addDenuncia(Integer userID, String denuncia)
    {
        if (denunciou(userID))
            return false;   // mesmo usuário não deve poder denunciar múltiplas vezes
        
        denunciaram.add(userID);
        denuncias.add(denuncia);
        return true;
    }

    public void removeDenuncia(String denuncia)
    {
        denuncias.remove(denuncia);
    }

    // usado somente em excluir usuário
    public void removeDenunciou(Integer userID)
    {
        denunciaram.remove(userID);
    }

    public void recebePropostaPatrocinio(Integer userID)
    {
        propostasPatrocinio.add(userID);
    }

    public void aceitaPropostaPatrocinio(Integer userID)
    {
        propostasPatrocinio.remove(userID);
        patrocinadores.add(userID);
    }

    public void rejeitaPropostaPatrocinio(Integer userID)
    {
        propostasPatrocinio.remove(userID);
    }

    // usado somente em excluir usuário
    public void removePatrocinio(Integer userID)
    {
        patrocinadores.remove(userID);
    }

    public boolean fezProposta(Integer userID)
    {
        return propostasPatrocinio.contains(userID);
    }

    public boolean isPatrocinador(Integer userID)
    {
        return patrocinadores.contains(userID);
    }
}
