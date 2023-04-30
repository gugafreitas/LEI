package Atividades;

import java.util.Date;

public class Corrida {
    private int id;
    private String descricao;
    private Date dataRealizacao;
    private int duracao;
    private float distancia;
    private float altimetria;
    private String percurso;

    public Corrida(int id, String descricao, Date dataRealizacao, int duracao, float distancia, float altimetria, String percurso) {
        this.id = id;

        this.descricao = descricao;
        this.dataRealizacao = dataRealizacao;
        this.duracao = duracao;
        this.distancia = distancia;
        this.altimetria = altimetria;
        this.percurso = percurso;
    }
}
