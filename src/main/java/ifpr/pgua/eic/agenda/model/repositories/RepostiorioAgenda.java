package ifpr.pgua.eic.agenda.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.daos.AgendaDAO;
import ifpr.pgua.eic.agenda.model.entities.Agenda;

public class RepostiorioAgenda {

    private ArrayList<Agenda> agendas;
    private AgendaDAO dao;

    public RepostiorioAgenda(AgendaDAO dao){
        agendas = new ArrayList<>();
        this.dao = dao;
    }

    public Resultado criarAgenda(String nome){
        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome inválido!");
        }

        Agenda agenda = new Agenda(nome);
        return dao.cadastrar(agenda);
    }

    public Resultado listarAgenda(){
        return dao.listar();
    }

    public Resultado buscarPorCodigo(int codigo){
        return dao.buscarPorCodigo(codigo);
    }

    public Resultado editarAgenda(String nome, Agenda antiga){
        if(antiga == null){
            return Resultado.erro("Agenda inválida");
        }

        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome inválido!");
        }

        Agenda nova = new Agenda(nome);
        nova.setCodigo(antiga.getCodigo());
        return dao.editar(nova, antiga);
    }

    public Resultado excluirAgenda(int codigo){
        return dao.excluir(codigo);
    }
}