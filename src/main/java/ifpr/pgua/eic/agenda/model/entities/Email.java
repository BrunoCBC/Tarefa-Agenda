package ifpr.pgua.eic.agenda.model.entities;

public class Email {
    
    private String email;
    private int codigo;
    
    public Email(String email, int codigo) {
        this.email = email;
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return email;
    }
}
