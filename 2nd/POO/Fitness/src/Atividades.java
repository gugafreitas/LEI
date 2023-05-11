import java.time.LocalDate;
import java.util.Date;

public abstract class Atividades {
    private String id;

    private String descricao;

    private LocalDate dataRealizacao;

    private int duracao;

    public Atividades(){
        this.id = "";
        this.descricao = "";
        this.dataRealizacao = LocalDate.EPOCH;
        this.duracao = 0;
    }

    public Atividades(String id, String descricao, LocalDate dataRealizacao, int duracao) {
        this.id = id;
        this.descricao = descricao;
        this.dataRealizacao = dataRealizacao;
        this.duracao = duracao;
    }

    public Atividades(Atividades outro){
        this.id = outro.getId();
        this.descricao = outro.getDescricao();
        this.dataRealizacao = outro.getDataRealizacao();
        this.duracao = outro.getDuracao();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String toString() {
        return "Atividade{" +
                "codigo='" + id + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data=" + dataRealizacao +
                ", duracao=" + duracao +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atividades atividade = (Atividades) o;
        return this.duracao == atividade.getDuracao()
                && this.id.equals(atividade.getId())
                && this.descricao.equals(atividade.getDescricao())
                && this.dataRealizacao.equals(atividade.getDataRealizacao());
    }


    public abstract double calorias(Utilizador u);
    public  abstract  Atividades clone( );
}
