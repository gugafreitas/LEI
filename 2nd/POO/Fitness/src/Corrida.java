import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Objects;

public class Corrida extends Atividades{
    private double distancia;
    private double altimetria;
    private String percurso;

    public Corrida(){
        super();
        this.distancia = 0;
        this.altimetria = 0;
        this.percurso = "";
    }

    public Corrida(String codigo, String descricao, LocalDate data, int duracao, double distancia, double altimetria, String percurso) {
        super(codigo, descricao, data, duracao);
        this.distancia = distancia;
        this.altimetria = altimetria;
        this.percurso = percurso;
    }

    public Corrida(Corrida outro) {
        super(outro);
        this.distancia = outro.getDistancia();
        this.altimetria = outro.getAltimetria();
        this.percurso = outro.getPercurso();
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getAltimetria() {
        return altimetria;
    }

    public void setAltimetria(double altimetria) {
        this.altimetria = altimetria;
    }

    public String getPercurso() {
        return percurso;
    }

    public void setPercurso(String percurso) {
        this.percurso = percurso;
    }

    public double calorias(Utilizador u) {
        long idade =  ChronoUnit.YEARS.between(LocalDate.now(),
                u.getData_nascimento());
        double calorias = distancia* u.getPeso()*getDuracao()*idade/50;
        return calorias;
    }


    public Atividades clone() {
        return new Corrida(this);
    }


    public String toString() {
        return "Corrida{" +
                super.toString()+
                "distancia=" + distancia +
                ", altimetria=" + altimetria +
                ", percurso='" + percurso + '\'' +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Corrida corrida = (Corrida) o;
        return Double.compare(corrida.getDistancia(), getDistancia()) == 0
                && Double.compare(corrida.getAltimetria(), getAltimetria()) == 0
                && this.percurso.equals(corrida.getPercurso());
    }

}
