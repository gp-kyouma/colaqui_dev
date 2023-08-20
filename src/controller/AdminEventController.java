package controller;

import model.Evento;
import model.Model;

// Controller para as operações que o administrador pode executar sobre eventos
// (excluir, avaliar denúncia)

public class AdminEventController {
    private RemoveEventController remove_controller;

    public AdminEventController(Model model)
    {
        remove_controller = new RemoveEventController(model);
    }

    public void ExcluirEvento(Evento evento)
    {
        remove_controller.RemoveEvent(evento, true);
    }

    public void DesconsiderarDenuncia(Evento evento, String denuncia)
    {
        evento.removeDenuncia(denuncia);
    }
}
