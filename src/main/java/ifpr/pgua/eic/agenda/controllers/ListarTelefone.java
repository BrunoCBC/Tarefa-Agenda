package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Telefone;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioTelefone;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ListarTelefone implements Initializable{

    @FXML
    private ListView<Telefone> lstTelefone;

    @FXML
    private TextArea txTelefone;

    private RepositorioTelefone repositorio;
    private RepostiorioAgenda repostiorioAgenda;

    public ListarTelefone(RepositorioTelefone repositorio, RepostiorioAgenda repostiorioAgenda) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
    }

    @FXML
    void editar(ActionEvent event) {
        Telefone telefone = lstTelefone.getSelectionModel().getSelectedItem();
        if(telefone != null){
            App.pushScreen("CADASTRARTELEFONE",o-> new CadastrarTelefone(repositorio, repostiorioAgenda, telefone));
            atualizarTela();
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION,"Deseja realmente deletar este telefone?");
        Optional<ButtonType> result = alert.showAndWait();
        Telefone telefone = lstTelefone.getSelectionModel().getSelectedItem();
        if(result.get() == ButtonType.OK && telefone != null){
            Resultado resultado;
            resultado = repositorio.excluirTelefone(telefone.getTelefone(), telefone.getCodigo());
            atualizarTela();
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
        txTelefone.setText(lstTelefone.getSelectionModel().getSelectedItem().getCodigo()+"\n"+lstTelefone.getSelectionModel().getSelectedItem().getTelefone());
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }

    public void atualizarTela(){
        lstTelefone.getItems().clear();
        txTelefone.clear();

        Resultado resultado = repositorio.listarTelefone();
        if(resultado.foiSucesso()){
            ArrayList<Telefone> telefones = (ArrayList)resultado.comoSucesso().getObj();
            lstTelefone.getItems().addAll(telefones);
        }else{
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        atualizarTela();
    }
}