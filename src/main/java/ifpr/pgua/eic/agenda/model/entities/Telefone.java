package ifpr.pgua.eic.agenda.model.entities;

public class Telefone {
    
    private int telefone;
    private int codigo;
    
    public Telefone(int telefone, int codigo) {
        this.telefone = telefone;
        this.codigo = codigo;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return telefone+"";
    }
}
