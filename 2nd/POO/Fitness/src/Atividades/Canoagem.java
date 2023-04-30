package Atividades;

import Utilizadores.Utilizador;
import jdk.jshell.execution.Util;

import java.util.Date;

public class Canoagem {
    private int id;
    private String descricao;
    private Date dataRealizacao;
    private int duracao;
    private String embarcacao;
    private float vento; //km/h
    private String direcaoVento;
    private float distancia;
    private float nrVoltas;

    public Canoagem(int id, String descricao, Date dataRealizacao, int duracao, String embarcacao, float vento, String direcaoVento, float distancia, float nrVoltas) {
        this.id = id;
        this.descricao = descricao;
        this.dataRealizacao = dataRealizacao;
        this.duracao = duracao;
        this.embarcacao = embarcacao;
        this.vento = vento;
        this.direcaoVento = direcaoVento;
        this.distancia = distancia;
        this.nrVoltas = nrVoltas;
    }

    public float valorCalorico(){
        Utilizador u;
        float valor = this.distancia * this.vento * this.duracao ;
        return valor;
    }
}
