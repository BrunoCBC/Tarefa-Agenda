package ifpr.pgua.eic.agenda.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Email;

public class JDBCEmailDAO implements EmailDAO{

    private static final String SQLINSERT = "INSERT INTO email(email,codigo) values (?,?)";
    private static final String SQLSELECT = "SELECT * FROM email";
    private static final String SQLSEARCH = "SELECT * FROM email WHERE codigo = ? AND email = ?";
    private static final String SQLUPDATE = "UPDATE email SET email = ?, codigo = ? WHERE codigo = ? AND email = ?";
    private static final String SQLDROP = "DELETE FROM email WHERE codigo = ? AND email = ?";
    private static final String SQLDROPALL = "DELETE FROM email WHERE codigo = ?";

    FabricaConexoes fabrica;

    public JDBCEmailDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastrar(Email email) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLINSERT);

            pstm.setString(1, email.getEmail());
            pstm.setInt(2, email.getCodigo());

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Email cadastrado!", email);
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
            ArrayList<Email> emails = new ArrayList<>();
            while(rs.next()){
                String emailtx = rs.getString("email");
                int codigo = rs.getInt("codigo");

                Email email = new Email(emailtx, codigo);
                emails.add(email);
            }
            return Resultado.sucesso("Email listado!", emails);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscarPorEmail(String emailtx, int codigotx){
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLSEARCH);

            pstm.setString(1, emailtx);
            pstm.setInt(2, codigotx);

            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                String emailRs = rs.getString("email");
                int codigoRs = rs.getInt("codigo");

                Email email = new Email(emailRs, codigoRs);
                return Resultado.sucesso("Email listado!", email);
            }
            return Resultado.erro("Erro desconhecido");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado editar(Email novo, Email antigo) {
        try (Connection con = fabrica.getConnection()){

            PreparedStatement pstm = con.prepareStatement(SQLUPDATE);

            pstm.setString(1, novo.getEmail());
            pstm.setInt(2, novo.getCodigo());
            pstm.setInt(3, antigo.getCodigo());
            pstm.setString(4, antigo.getEmail());

            int ret = pstm.executeUpdate();

            if(ret == 1){
                return Resultado.sucesso("Editado com sucesso", novo);
            }

            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado excluir(String email, int codigo) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLDROP);

            pstm.setInt(1, codigo);
            pstm.setString(2, email);

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Email deletado com sucesso!", null);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado excluirTodos(int codigo) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLDROPALL);

            pstm.setInt(1, codigo);

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Email deletado com sucesso!", null);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
}