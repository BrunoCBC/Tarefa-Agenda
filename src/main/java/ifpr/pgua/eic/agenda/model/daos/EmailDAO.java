package ifpr.pgua.eic.agenda.model.daos;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Email;

public interface EmailDAO {
    
    public Resultado cadastrar(Email email);

    public Resultado listar();
    public Resultado buscarPorEmail(String email, int codigo);

    public Resultado editar(Email novo, Email antigo);

    public Resultado excluir(String email, int codigo);
    public Resultado excluirTodos(int codigo);
}
