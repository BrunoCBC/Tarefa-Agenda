package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Agenda;
import ifpr.pgua.eic.agenda.model.entities.Email;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioEmail;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastrarEmail implements Initializable{

    @FXML
    private ComboBox<Agenda> cbAgenda;

    @FXML
    private TextField txEmail;

    private RepositorioEmail repositorio;
    private RepostiorioAgenda repostiorioAgenda;

    private Email email;

    public CadastrarEmail(RepositorioEmail repositorio, RepostiorioAgenda repostiorioAgenda) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
    }

    public CadastrarEmail(RepositorioEmail repositorio, RepostiorioAgenda repostiorioAgenda, Email email) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
        this.email = email;
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String emailTx = txEmail.getText();
        int codigo = cbAgenda.getSelectionModel().getSelectedItem().getCodigo();

        Resultado resultado;
        if(email == null){
            resultado = repositorio.criarEmail(emailTx, codigo);
        }else{
            resultado = repositorio.editarEmail(emailTx, codigo, email);
        }
        
        Alert alert;
        if(resultado.foiSucesso()){
            alert = new Alert(AlertType.INFORMATION,resultado.getMsg());
            txEmail.clear();
            cbAgenda.getSelectionModel().clearSelection();
            cbAgenda.setValue(null);
        }else{
            alert = new Alert(AlertType.ERROR,resultado.getMsg());
        }
        alert.showAndWait();
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        cbAgenda.getItems().clear();

        Resultado r = repostiorioAgenda.listarAgenda();
        if(r.foiSucesso()){
            ArrayList<Agenda> agendas = (ArrayList<Agenda>)r.comoSucesso().getObj();
            cbAgenda.getItems().addAll(agendas);
        }else{
            Alert alert = new Alert(AlertType.ERROR,r.getMsg());
            alert.showAndWait();
        }

        if(email != null){
            txEmail.setText(email.getEmail());
            Resultado resultado = repostiorioAgenda.buscarPorCodigo(email.getCodigo());
            System.out.println(email.getCodigo()+"");
            Agenda agenda = (Agenda)resultado.comoSucesso().getObj();
            cbAgenda.setValue(agenda);
        }
    }
}