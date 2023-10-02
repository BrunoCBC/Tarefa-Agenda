package ifpr.pgua.eic.agenda.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.daos.TelefoneDAO;
import ifpr.pgua.eic.agenda.model.entities.Telefone;

public class RepositorioTelefone {

    private ArrayList<Telefone> telefones;
    private TelefoneDAO dao;

    public RepositorioTelefone(TelefoneDAO dao){
        telefones = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado cirarTelefone(int telefone, int codigo){

        if(telefone < 0){
            return Resultado.erro("Telefone inválido!");
        }

        Resultado resultado = dao.buscarPorTelefone(telefone, codigo);
        if(resultado.foiErro()){
            Telefone telefoneObj = new Telefone(telefone, codigo);
            return dao.cadastrar(telefoneObj);
        }
        return Resultado.erro("Telefone já cadastrado nesta agenda!");
    }

    public Resultado listarTelefone(){
        return dao.listar();
    }

    public Resultado editarTelefone(int telefone, int codigo, Telefone antigo){

        if(telefone < 0){
            return Resultado.erro("Telefone inválido!");
        }

        Resultado resultado = dao.buscarPorTelefone(telefone, codigo);
        if(resultado.foiErro()){
            Telefone novo = new Telefone(telefone, codigo);
            return dao.editar(novo, antigo);
        }
        return Resultado.erro("Telefone com está informação já cadastrado nesta agenda!");
    }

    public Resultado excluirTelefone(int telefone, int codigo){
        return dao.excluir(telefone, codigo);
    }

    public Resultado excluirTodosTelefone(int codigo){
        return dao.excluirTodos(codigo);
    }
}