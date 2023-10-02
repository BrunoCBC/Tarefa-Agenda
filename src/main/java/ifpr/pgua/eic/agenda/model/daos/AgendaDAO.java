package ifpr.pgua.eic.agenda.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Agenda;

public interface AgendaDAO {
    
    public Resultado cadastrar(Agenda agenda);

    public Resultado listar();
    public Resultado buscarPorCodigo(int codigo);

    public Resultado editar(Agenda nova, Agenda antiga);

    public Resultado excluir(int codigo);
}
