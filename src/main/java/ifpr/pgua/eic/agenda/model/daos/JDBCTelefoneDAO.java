package ifpr.pgua.eic.agenda.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;

import ifpr.pgua.eic.agenda.model.entities.Telefone;

public class JDBCTelefoneDAO implements TelefoneDAO{

    private static final String SQLINSERT = "INSERT INTO telefone(telefone,codigo) values (?,?)";
    private static final String SQLSELECT = "SELECT * FROM telefone";
    private static final String SQLSEARCH = "SELECT * FROM telefone WHERE codigo = ? AND telefone = ?";
    private static final String SQLUPDATE = "UPDATE telefone SET telefone = ?, codigo = ? WHERE codigo = ? AND telefone = ?";
    private static final String SQLDROP = "DELETE FROM telefone WHERE codigo = ? AND telefone = ?";
    private static final String SQLDROPALL = "DELETE FROM telefone WHERE codigo = ?";

    FabricaConexoes fabrica;

    public JDBCTelefoneDAO(FabricaConexoes fabrica) {
        this.fabrica = fabrica;
    }

    @Override
    public Resultado cadastrar(Telefone telefone) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLINSERT);

            pstm.setInt(1, telefone.getTelefone());
            pstm.setInt(2, telefone.getCodigo());

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Telefone cadastrado com sucesso", telefone);
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
            ArrayList<Telefone> telefones = new ArrayList<>();
            while(rs.next()){
                int telefonetx = rs.getInt("telefone");
                int codigo = rs.getInt("codigo");

                Telefone telefone = new Telefone(telefonetx, codigo);
                telefones.add(telefone);
            }
            return Resultado.sucesso("Telefones listados!", telefones);
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado buscarPorTelefone(int telefoneTx, int codigo){
        try(Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLSEARCH);

            pstm.setInt(1, telefoneTx);
            pstm.setInt(2, codigo);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()){
                int telefoneRs = rs.getInt("telefone");
                int codigoRs = rs.getInt("codigo");

                Telefone telefone = new Telefone(telefoneRs, codigoRs);
                return Resultado.sucesso("Telefone listado!", telefone);
            }
            return Resultado.erro("Erro desconhecido!");
        }catch(SQLException e){
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado editar(Telefone novo, Telefone antigo) {
        try (Connection con = fabrica.getConnection()){

            PreparedStatement pstm = con.prepareStatement(SQLUPDATE);

            pstm.setInt(1, novo.getTelefone());
            pstm.setInt(2, novo.getCodigo());
            pstm.setInt(3, antigo.getCodigo());
            pstm.setInt(4, antigo.getTelefone());

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
    public Resultado excluir(int telefone, int codigo) {
        try (Connection con = fabrica.getConnection()){
            PreparedStatement pstm = con.prepareStatement(SQLDROP);

            pstm.setInt(1, codigo);
            pstm.setInt(2, telefone);

            int ret = pstm.executeUpdate();
            if(ret == 1){
                return Resultado.sucesso("Telefone deletado com sucesso!", null);
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
                return Resultado.sucesso("Telefone deletado com sucesso!", null);
            }
            return Resultado.erro("Erro desconhecido!");
        } catch (Exception e) {
            return Resultado.erro(e.getMessage());
        }
    }
}