package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Agenda;
import ifpr.pgua.eic.agenda.model.entities.Telefone;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioTelefone;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CadastrarTelefone implements Initializable{

    @FXML
    private ComboBox<Agenda> cbAgenda;

    @FXML
    private TextField txTelefone;

    private RepositorioTelefone repositorio;
    private RepostiorioAgenda repostiorioAgenda;

    private Telefone telefone;

    public CadastrarTelefone(RepositorioTelefone repositorio, RepostiorioAgenda repostiorioAgenda) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
    }

    public CadastrarTelefone(RepositorioTelefone repositorio, RepostiorioAgenda repostiorioAgenda, Telefone telefone) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
        this.telefone = telefone;
    }

    @FXML
    void cadastrar(ActionEvent event) {

        int telefonetx = Integer.parseInt(txTelefone.getText());
        int codigo = cbAgenda.getSelectionModel().getSelectedItem().getCodigo();

        Resultado resultado;
        if(telefone == null){
            resultado = repositorio.cirarTelefone(telefonetx, codigo);
        }else{
            resultado = repositorio.editarTelefone(telefonetx, codigo, telefone);
        }

        Alert alert;
        if(resultado.foiSucesso()){
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            txTelefone.clear();
            cbAgenda.getSelectionModel().clearSelection();
            cbAgenda.setValue(null);
        }else{
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
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

        if(telefone != null){
            txTelefone.setText(telefone.getTelefone()+"");
            Resultado resultado = repostiorioAgenda.buscarPorCodigo(telefone.getCodigo());
            Agenda agenda = (Agenda)resultado.comoSucesso().getObj();
            cbAgenda.getSelectionModel().select(agenda);
        }
    }
}