package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Agenda;
import ifpr.pgua.eic.agenda.model.entities.Email;
import ifpr.pgua.eic.agenda.model.entities.Telefone;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioEmail;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioTelefone;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class ListarAgenda implements Initializable{

    @FXML
    private ListView<Agenda> lstAgenda;

    @FXML
    private TextArea txAgenda;

    private RepostiorioAgenda repositorio;
    private RepositorioEmail repositorioEmail;
    private RepositorioTelefone repositorioTelefone;

    public ListarAgenda(RepostiorioAgenda repositorio, RepositorioEmail repositorioEmail,
            RepositorioTelefone repositorioTelefone) {
        this.repositorio = repositorio;
        this.repositorioEmail = repositorioEmail;
        this.repositorioTelefone = repositorioTelefone;
    }

    @FXML
    void editar(ActionEvent event) {
        Agenda agenda = lstAgenda.getSelectionModel().getSelectedItem();

        if(agenda != null){
            App.pushScreen("CADASTRARAGENDA",o-> new CadastrarAgenda(repositorio, agenda));
            atualizarLst();
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION,"Deseja realmente deletar esta agenda, os telefones e emails ligados a ele?");
        Optional<ButtonType> result = alert.showAndWait();
        Agenda agenda = lstAgenda.getSelectionModel().getSelectedItem();
        if(result.get() == ButtonType.OK && agenda != null){
            Resultado resultado;
            repositorioTelefone.excluirTodosTelefone(agenda.getCodigo());
            repositorioEmail.excluirTodosEmail(agenda.getCodigo());
            resultado = repositorio.excluirAgenda(agenda.getCodigo());
            atualizarLst();
            if(resultado.foiSucesso()){
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }else{
                alert = new Alert(AlertType.ERROR, resultado.getMsg());
            }
            alert.showAndWait();
        }
    }

    @FXML
    void selecionar(MouseEvent event) {
        Agenda agenda = lstAgenda.getSelectionModel().getSelectedItem();
        Resultado resultado = repositorioEmail.listarEmail();
        String emailTx = "";
        if(resultado.foiSucesso()){
            ArrayList<Email> emails = (ArrayList)resultado.comoSucesso().getObj(); 
            for(Email email:emails){
                if(email.getCodigo() == agenda.getCodigo()){
                    emailTx += email.toString()+"\n";
                }
            }
        }

        resultado = repositorioTelefone.listarTelefone();
        String telefoneTx = "";
        if(resultado.foiSucesso()){
            ArrayList<Telefone> telefones = (ArrayList)resultado.comoSucesso().getObj();
            for(Telefone telefone:telefones){
                if(telefone.getCodigo() == agenda.getCodigo()){
                    telefoneTx += telefone.toString()+"\n";
                }
            }
        }

        txAgenda.setText(lstAgenda.getSelectionModel().getSelectedItem().getCodigo()+"\n"+lstAgenda.getSelectionModel().getSelectedItem().getNome()+"\n\nEmail:\n\n"+emailTx+"\nTelefone:\n\n"+telefoneTx);
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    public void atualizarLst(){
        lstAgenda.getItems().clear();
        txAgenda.clear();

        Resultado resultado = repositorio.listarAgenda();
        if(resultado.foiSucesso()){
            ArrayList<Agenda> agendas = (ArrayList)resultado.comoSucesso().getObj();
            lstAgenda.getItems().addAll(agendas);
        }else{
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        atualizarLst();
    }

}
