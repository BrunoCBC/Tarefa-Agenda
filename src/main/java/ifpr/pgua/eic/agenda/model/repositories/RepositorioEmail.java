package ifpr.pgua.eic.agenda.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.daos.EmailDAO;
import ifpr.pgua.eic.agenda.model.entities.Email;

public class RepositorioEmail {
    
    private ArrayList<Email> emails;
    private EmailDAO dao;

    public RepositorioEmail(EmailDAO dao) {
        emails = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado criarEmail(String email, int codigo){
        if(email.isBlank() || email.isEmpty()){
            return Resultado.erro("Email inválido");
        }

        Resultado resultado = dao.buscarPorEmail(email, codigo);
        if(resultado.foiErro()){
            Email emailObj = new Email(email, codigo);
            return dao.cadastrar(emailObj);
        }
        return Resultado.erro("Email já cadastrado nesta agenda");
    }

    public Resultado listarEmail(){
        return dao.listar();
    }

    public Resultado editarEmail(String email, int codigo, Email antigo){

        if(email.isBlank() || email.isEmpty()){
            return Resultado.erro("Email inválido");
        }

        Resultado resultado = dao.buscarPorEmail(email, codigo);
        if(resultado.foiErro()){
            Email novo = new Email(email, codigo);
            return dao.editar(novo, antigo);
        }
        return Resultado.erro("Email com está informações já cadastrado nesta agenda!");
    }   

    public Resultado excluirEmail(String email, int codigo){
        return dao.excluir(email, codigo);
    }

    public Resultado excluirTodosEmail(int codigo){
        return dao.excluirTodos(codigo);
    }
}
