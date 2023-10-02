package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Agenda;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastrarAgenda implements Initializable{

    @FXML
    private TextField txNome;

    private RepostiorioAgenda repositorio;
    private Agenda agenda;

    public CadastrarAgenda(RepostiorioAgenda repositorio) {
        this.repositorio = repositorio;
    }

    public CadastrarAgenda(RepostiorioAgenda repositorio, Agenda agenda) {
        this.repositorio = repositorio;
        this.agenda = agenda;
    }

    @FXML
    void cadastrar(ActionEvent event) {
        String nome = txNome.getText();

        Resultado resultado;
        if(agenda == null){
            resultado = repositorio.criarAgenda(nome);
        }else{
            resultado = repositorio.editarAgenda(nome, agenda);
        }

        Alert alert;
        if(resultado.foiSucesso()){
            alert = new Alert(AlertType.INFORMATION,resultado.getMsg());
            txNome.clear();
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
        if(agenda != null){
            txNome.setText(agenda.getNome());        
        }
    }
}
