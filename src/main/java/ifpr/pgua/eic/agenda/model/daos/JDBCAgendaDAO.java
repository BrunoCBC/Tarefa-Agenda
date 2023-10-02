package ifpr.pgua.eic.agenda.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Agenda;
import ifpr.pgua.eic.agenda.utils.DBUtils;

public class JDBCAgendaDAO implements AgendaDAO{

    private static final String SQLINSERT = "INSERT INTO agenda(nome) values (?)";
    private static final String SQLSELECT = "SELECT * FROM agenda";
    private static final String SQLSEARCH = "SELECT * FROM agenda where codigo = ?";
    private static final String SQLUPDATE = "UPDATE agenda SET nome = ? WHERE codigo = ?";
    private static final String SQLDROP = "DELETE FROM agenda WHERE codigo = ?";

    FabricaConexoes fabrica;

    public JDBCAgendaDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastrar(Agenda agenda) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLINSERT,Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, agenda.getNome());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                int codigo = DBUtils.getLastId(pstm);

                agenda.setCodigo(codigo);
                return Resultado.sucesso("Agenda cadastrada!", agenda);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listar() {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLSELECT);
            
            ResultSet rs = pstm.executeQuery();

            ArrayList<Agenda> agendas = new ArrayList<>();
            while(rs.next()){
                int codigo = rs.getInt("codigo");
                String nome = rs.getString("nome");

                Agenda agenda = new Agenda(nome);
                agenda.setCodigo(codigo);

                agendas.add(agenda);
            }

            return Resultado.sucesso("Agenda listada!", agendas);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscarPorCodigo(int codigo) {
        try (Connection con = fabrica.getConnection()){

            PreparedStatement pstm = con.prepareStatement(SQLSEARCH);

            pstm.setInt(1, codigo);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                String nome = rs.getString("nome");

                Agenda agenda = new Agenda(nome);
                agenda.setCodigo(codigo);

                return Resultado.sucesso("Agenda listada", agenda);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado editar(Agenda nova, Agenda antiga) {
        try (Connection con = fabrica.getConnection()){

            PreparedStatement pstm = con.prepareStatement(SQLUPDATE);

            pstm.setString(1, nova.getNome());
            pstm.setInt(2, antiga.getCodigo());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                return Resultado.sucesso("Editado com sucesso", nova);
            }

            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado excluir(int codigo) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLDROP);

            pstm.setInt(1, codigo);

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Agenda deletada com sucesso!", null);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }    
}
