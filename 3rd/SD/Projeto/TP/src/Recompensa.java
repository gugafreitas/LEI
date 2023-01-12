import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Recompensa{
    private Trotinete trotinete;
    private Coordenada destino;
    private double recompensa;

    public Recompensa() {
        this.trotinete = new Trotinete();
        this.destino = new Coordenada();
        this.recompensa = 0;
    }

    public Recompensa(Trotinete trotinete, Coordenada destino) {
        this.trotinete = trotinete;
        this.destino = destino;
        this.recompensa = trotinete.getDist(destino);
    }

    public Recompensa(Trotinete trotinete, Coordenada destino, double recompensa) {
        this.trotinete = trotinete;
        this.destino = destino;
        this.recompensa = recompensa;
    }

    public Trotinete getTrotinete() {
        return trotinete;
    }

    public Coordenada getDestino() {
        return destino;
    }

    public double getRecompensa() {
        return recompensa;
    }

    public void setTrotinete(Trotinete trotinete) {
        this.trotinete = trotinete;
    }

    public void setDestino(Coordenada destino) {
        this.destino = destino;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public void serialize(DataOutputStream out) throws IOException {
        trotinete.serialize(out);
        destino.serialize(out);
        out.writeDouble(recompensa);
    }

    public static Recompensa deserialize(DataInputStream in) throws IOException{
        Trotinete t = Trotinete.deserialize(in);
        Coordenada c = Coordenada.deserialize(in);
        Double r = in.readDouble();
        return new Recompensa(t,c,r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recompensa that = (Recompensa) o;
        return recompensa == that.recompensa && Objects.equals(trotinete, that.trotinete) && Objects.equals(destino, that.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trotinete, destino, recompensa);
    }

    @Override
    public String toString() {
        return "Recompensa:" +
                "\n\tTotinete=" + trotinete +
                "\n\tDestino=" + destino +
                "\n\tRecompensa=" + recompensa+"€";
    }
}
