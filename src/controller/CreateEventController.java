package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import model.Model;
import model.Evento;

public class CreateEventController {
    private Model model;

    public CreateEventController(Model model)
    {
        this.model = model;
    }

    public String CriarEvento(String nome, String descricao, String local, Date data, Date horario, Integer maxVagas)
    {
        // checar se a data é correta (no futuro, não pode criar evento no passado)
        LocalDate local_date = LocalDate.ofInstant(data.toInstant(), ZoneId.systemDefault());
        LocalTime local_time = LocalTime.ofInstant(horario.toInstant(), ZoneId.systemDefault());
        if (LocalDateTime.of(local_date, local_time).isBefore(LocalDateTime.now()))
            return "data_invalida";
        
        // checar se o numero de vagas é positivo
        if (maxVagas <= 0)
            return "vagas_invalido";

        // Se tudo ok, insere no evento na lista de eventos

        local_time = local_time.withSecond(0).withNano(0);
        int new_id = 0;
        for (Evento i : model.getEventoList())
        {
            if (new_id < i.getID())
                new_id = i.getID();
        }
        new_id++;
        model.addEventoToList(new Evento(new_id,nome,model.getLoggedUser().getCartao(),model.getLoggedUser().getNome(),descricao,local,local_date,local_time,maxVagas));
        model.saveEventos();

        // e atualiza lista de eventos criados no usuário logado
        model.getLoggedUser().addMeuEvento(new_id);
        model.saveUsuarios();

        return "ok";
    }
}
