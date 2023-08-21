package controller;

import model.Evento;
import model.Model;
import model.Usuario;

public class RemoveUserController {
    private Model model;

    private RemoveEventController remove_controller;

    public RemoveUserController(Model model)
    {
        this.model = model;
        remove_controller = new RemoveEventController(model);
    }

    public void RemoveUser(Usuario usuario)
    {
        // Testar se usuário existe
        if (!model.isUsuarioOnList(usuario.getCartao()))
            return;

        // Remove todos os eventos desse usuário
        for (int i = 0; i < usuario.getMeusEventos().size(); i++)
            remove_controller.RemoveEvent(model.getEventoFromList(usuario.getMeusEventos().get(i)), false);

        // Remove usuário de todos os eventos
        for (Evento i : model.getEventoList())
        {
            if (i.confirmouPresenca(usuario.getCartao()))
                i.removePresenca(usuario.getCartao());

            if (i.solicitouPresencaExcedente(usuario.getCartao()))
                i.removePresencaExcedente(usuario.getCartao());

            if (i.avaliou(usuario.getCartao()))
                i.removeAvaliou(usuario.getCartao());

            if (i.denunciou(usuario.getCartao()))
                i.removeDenunciou(usuario.getCartao());

            if (i.fezProposta(usuario.getCartao()))
                i.rejeitaPropostaPatrocinio(usuario.getCartao());

            if (i.isPatrocinador(usuario.getCartao()))
                i.removePatrocinio(usuario.getCartao());

            model.updateEventoOnList(i.getID(),i);
        }

        model.saveEventos();

        model.removeUsuarioFromList(usuario.getCartao());
        model.saveUsuarios();
    }
}
