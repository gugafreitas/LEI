import java.time.LocalDate;

public class Abdominais extends Atividades{
    private String tipo;
    private int repeticoes;

    public Abdominais() {
        super();
        this.tipo = "";
        this.repeticoes = 0;
    }

    public Abdominais(String codigo, String descricao, LocalDate data, int duracao, String tipo, int repeticoes) {
        super(codigo, descricao, data, duracao);
        this.tipo = tipo;
        this.repeticoes = repeticoes;
    }

    public Abdominais(Abdominais outro) {
        super(outro);
        this.tipo = outro.getTipo();
        this.repeticoes = outro.getRepeticoes();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    /**
     * Nota: a fórmula está diferente ao que é pedido no enunciado.
     */
    public double calorias(Utilizador u) {
        return getDuracao()*repeticoes*3/5*(1+1/u.getPeso());
    }


    public Atividades clone() {
        return new Abdominais(this);
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Abdominais abdominal = (Abdominais) o;
        return getRepeticoes() == abdominal.getRepeticoes()
                && this.tipo.equals(abdominal.getTipo());
    }


    public String toString() {
        return "Abdominal{" +
                super.toString() +
                "tipo='" + tipo + '\'' +
                ", repeticoes=" + repeticoes +
                '}';
    }
}
