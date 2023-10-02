package ifpr.pgua.eic.agenda.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.App;
import ifpr.pgua.eic.agenda.model.entities.Email;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioEmail;
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

public class ListarEmail implements Initializable{

    @FXML
    private ListView<Email> lstEmail;

    @FXML
    private TextArea txEmail;

    private RepositorioEmail repositorio;
    private RepostiorioAgenda repostiorioAgenda;

    public ListarEmail(RepositorioEmail repositorio, RepostiorioAgenda repostiorioAgenda) {
        this.repositorio = repositorio;
        this.repostiorioAgenda = repostiorioAgenda;
    }

    @FXML
    void editar(ActionEvent event) {
        Email email = lstEmail.getSelectionModel().getSelectedItem();
        
        if(email != null){
            App.pushScreen("CADASTRAREMAIL",o-> new CadastrarEmail(repositorio, repostiorioAgenda, email));
            atualizarLst();
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION,"Deseja realmente deletar este email?");
        Optional<ButtonType> result = alert.showAndWait();
        Email email = lstEmail.getSelectionModel().getSelectedItem();
        if(result.get() == ButtonType.OK && email != null){
            Resultado resultado;
            resultado = repositorio.excluirEmail(email.getEmail(), email.getCodigo());
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
        txEmail.setText(lstEmail.getSelectionModel().getSelectedItem().getCodigo()+"\n"+lstEmail.getSelectionModel().getSelectedItem().getEmail());
    }

    @FXML
    void voltar(ActionEvent event) {
        App.popScreen();
    }
    
    public void atualizarLst(){
        lstEmail.getItems().clear();
        txEmail.clear();
        
        Resultado resultado = repositorio.listarEmail();
        if(resultado.foiSucesso()){
            ArrayList<Email> emails = (ArrayList)resultado.comoSucesso().getObj();
            lstEmail.getItems().addAll(emails);
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