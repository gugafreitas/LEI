import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Utilizador implements Serializable{
    private String id;
    private String name;
    private int passwordHash;
    private Coordenada coordenada;
    private boolean notificacao;
    private double raio;

    public Utilizador() {
        this.id = "NaN";
        this.name = "NaN";
        this.passwordHash = 0;
        this.coordenada = new Coordenada(-1,-1);
        this.raio = 0D;
    }

    public Utilizador(String id, String name, int passwordHash, Coordenada c) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.coordenada = c;
        this.notificacao = false;
        this.raio = 0D;
    }

    public Utilizador(String id, String name, int passwordHash, Coordenada c,boolean n,double r) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.coordenada = c;
        this.notificacao = n;
        this.raio = r;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public boolean isNotificacao() {
        return notificacao;
    }

    public void setNotificacao(boolean notificacao) {
        this.notificacao = notificacao;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public void serialize(DataOutputStream out) throws IOException{
        out.writeUTF(this.id);
        out.writeUTF(this.name);
        out.writeInt(this.passwordHash);
        out.writeInt(this.coordenada.getX());
        out.writeInt(this.coordenada.getY());
        out.writeBoolean(this.notificacao);
        out.writeDouble(this.raio);
    }

    public static Utilizador deserialize(DataInputStream in) throws IOException{
        String id = in.readUTF();
        String name = in.readUTF();
        int passwordHash = in.readInt();
        int x = in.readInt();
        int y = in.readInt();
        Coordenada c = new Coordenada(x,y);
        boolean n = in.readBoolean();
        double r = in.readDouble();
        return new Utilizador(id,name,passwordHash,c,n,r);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Utilizador)) {
            return false;
        }
        return Objects.equals(id, ((Utilizador)o).id) && Objects.equals(name, ((Utilizador)o).name) && Objects.equals(passwordHash, ((Utilizador)o).passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, passwordHash);
    }

    @Override
    public String toString() {
        return "Utilizador:" +
            " id=" + getId() +
            ", Name=" + getName() +
            ", passwordHash=" + getPasswordHash() +
            "\n";
    }

}
