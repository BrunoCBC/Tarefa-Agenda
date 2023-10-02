package ifpr.pgua.eic.agenda.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Telefone;

public interface TelefoneDAO {
    
    public Resultado cadastrar(Telefone telefone);

    public Resultado listar();
    public Resultado buscarPorTelefone(int telefone, int codigo);

    public Resultado editar(Telefone novo, Telefone antiga);

    public Resultado excluir(int telefone, int codigo);
    public Resultado excluirTodos(int codigo);
}
