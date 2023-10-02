package ifpr.pgua.eic.agenda;

import ifpr.pgua.eic.agenda.controllers.CadastrarAgenda;
import ifpr.pgua.eic.agenda.controllers.CadastrarEmail;
import ifpr.pgua.eic.agenda.controllers.CadastrarTelefone;
import ifpr.pgua.eic.agenda.controllers.ListarAgenda;
import ifpr.pgua.eic.agenda.controllers.ListarEmail;
import ifpr.pgua.eic.agenda.controllers.ListarTelefone;
import ifpr.pgua.eic.agenda.controllers.Principal;
import ifpr.pgua.eic.agenda.model.daos.AgendaDAO;
import ifpr.pgua.eic.agenda.model.daos.EmailDAO;
import ifpr.pgua.eic.agenda.model.daos.FabricaConexoes;
import ifpr.pgua.eic.agenda.model.daos.JDBCAgendaDAO;
import ifpr.pgua.eic.agenda.model.daos.JDBCEmailDAO;
import ifpr.pgua.eic.agenda.model.daos.JDBCTelefoneDAO;
import ifpr.pgua.eic.agenda.model.daos.TelefoneDAO;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioEmail;
import ifpr.pgua.eic.agenda.model.repositories.RepositorioTelefone;
import ifpr.pgua.eic.agenda.model.repositories.RepostiorioAgenda;
import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

public class App extends BaseAppNavigator{

    private AgendaDAO agendaDAO = new JDBCAgendaDAO(FabricaConexoes.getInstance());
    private RepostiorioAgenda repostiorioAgenda = new RepostiorioAgenda(agendaDAO);
    private TelefoneDAO telefoneDAO = new JDBCTelefoneDAO(FabricaConexoes.getInstance());
    private RepositorioTelefone repositorioTelefone = new RepositorioTelefone(telefoneDAO);
    private EmailDAO emailDAO = new JDBCEmailDAO(FabricaConexoes.getInstance());
    private RepositorioEmail repositorioEmail = new RepositorioEmail(emailDAO);

    public static void main(String[] args){
        launch();
    }

    @Override
    public String getAppTitle() {
        return "Agenda de Contatos";
    }

    @Override
    public String getHome() {
        return "PRINCIPAL";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(App.class,"principal.fxml",o->new Principal()));
        registraTela("CADASTRARAGENDA", new ScreenRegistryFXML(App.class,"cadastrar_agenda.fxml",o-> new CadastrarAgenda(repostiorioAgenda)));
        registraTela("CADASTRARTELEFONE", new ScreenRegistryFXML(App.class,"cadastrar_telefone.fxml",o-> new CadastrarTelefone(repositorioTelefone, repostiorioAgenda)));
        registraTela("CADASTRAREMAIL", new ScreenRegistryFXML(App.class,"cadastrar_email.fxml",o-> new CadastrarEmail(repositorioEmail, repostiorioAgenda)));
        registraTela("LISTARAGENDA", new ScreenRegistryFXML(App.class,"listar_agenda.fxml",o-> new ListarAgenda(repostiorioAgenda, repositorioEmail, repositorioTelefone)));
        registraTela("LISTARTELEFONE", new ScreenRegistryFXML(App.class,"listar_telefone.fxml",o-> new ListarTelefone(repositorioTelefone, repostiorioAgenda)));
        registraTela("LISTAREMAIL", new ScreenRegistryFXML(App.class,"listar_email.fxml",o-> new ListarEmail(repositorioEmail, repostiorioAgenda)));
    }    
}