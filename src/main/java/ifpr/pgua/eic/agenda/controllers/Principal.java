package ifpr.pgua.eic.agenda.controllers;

import ifpr.pgua.eic.agenda.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Principal {

    @FXML
    void cadastrarAgenda(ActionEvent event) {
        App.pushScreen("CADASTRARAGENDA");
    }

    @FXML
    void cadastrarEmail(ActionEvent event) {
        App.pushScreen("CADASTRAREMAIL");
    }

    @FXML
    void cadastrarTelefone(ActionEvent event) {
        App.pushScreen("CADASTRARTELEFONE");
    }

    @FXML
    void listarAgenda(ActionEvent event) {
        App.pushScreen("LISTARAGENDA");
    }

    @FXML
    void listarEmail(ActionEvent event) {
        App.pushScreen("LISTAREMAIL");
    }

    @FXML
    void listarTelefone(ActionEvent event) {
        App.pushScreen("LISTARTELEFONE");
    }

}