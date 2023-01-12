package src;

import java.util.ArrayList;
import java.util.Map;

import src.Campeonatos.Campeonato;
import src.Carros.Carro;
import src.Circuitos.Circuito;
import src.Circuitos.Elemento;
import src.Pilotos.Piloto;

public class Jogar {
    private Carro carro;
    private Campeonato campeonato;
    private Piloto piloto;
    private char tipo;

    public Jogar() {
        this.carro = new Carro();
        this.campeonato = new Campeonato();
        this.piloto = new Piloto();
        this.tipo = ' ';
    }

    public Jogar(Jogar j) {
        this.carro = j.getCarro();
        this.campeonato = j.getCampeonato();
        this.piloto = j.getPiloto();
        this.tipo = j.getTipo();
    }

    public Jogar(Carro carro, Campeonato campeonato, Piloto piloto, char tipo) {
        this.carro = carro;
        this.campeonato = campeonato;
        this.piloto = piloto;
        this.tipo = tipo;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return this.carro;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Campeonato getCampeonato() {
        return this.campeonato;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public Piloto getPiloto() {
        return this.piloto;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public char getTipo() {
        return this.tipo;
    }

    public void startSim(String uID, Campeonato campeonato, Piloto piloto, Carro carro) {
        Piloto pilotoBot1 = new Piloto("Paulo", false, Float.parseFloat("0.5"), Float.parseFloat("0.5"));
        Carro carroBot1 = new Carro(1, "BMW", "C1", Float.parseFloat("0.5"), Float.parseFloat("0.5"), 30, "duro", 10,
                false);

        Piloto pilotoBot2 = new Piloto("Joana", false, Float.parseFloat("0.3"), Float.parseFloat("0.7"));
        Carro carroBot2 = new Carro(2, "BMW", "C2", Float.parseFloat("0.4"), Float.parseFloat("0.6"), 35, "duro", 9,
                false);

        for (Map.Entry<String, Circuito> pair : campeonato.getCircuitos().entrySet()) {
            Circuito temp = pair.getValue();
            for (Map.Entry<Integer, Elemento> aux : temp.getComprimento().entrySet()) {
                System.out.println("You are first.");
            }
        }

    }
}