package Atividades;

import java.util.Date;

public class Abdominais {
    private int id;
    private String descricao;
    private Date dataRealizacao;
    private int duracao;
    private String tipo;
    private int nrReps;

    public Abdominais(int id, String descricao, Date dataRealizacao, int duracao, String tipo, int nrReps) {
        this.id = id;
        this.descricao = descricao;
        this.dataRealizacao = dataRealizacao;
        this.duracao = duracao;
        this.tipo = tipo;
        this.nrReps = nrReps;
    }

    public float valorCalorico(){
        float valor = this.duracao * this.nrReps * 3 / 5;
        return valor;
    }
}
