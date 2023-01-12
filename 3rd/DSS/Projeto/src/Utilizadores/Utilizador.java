package src.Utilizadores;

import src.Pilotos.Piloto;
import src.Campeonatos.Campeonato;
import src.Carros.Carro;

public class Utilizador {
    private String nome_utilizador;
    private String password;
    private char tipoDeUser; //n é normal, p é premium, a é admin
    private Campeonato campeonato_selecionado;
    private Piloto piloto_selecionado ; 
    private Carro carro_selecionado;



    public Utilizador(){
        this.nome_utilizador = "";
        this.password = "";
        this.tipoDeUser = ' ';
        this.campeonato_selecionado = new Campeonato();
        this.piloto_selecionado = new Piloto();
        this.carro_selecionado = new Carro();
    }

    public Utilizador(String nu, String pass, char tipo, Campeonato campeonato, Piloto piloto, Carro carro){
        this.nome_utilizador = nu;
        this.password = pass;
        this.tipoDeUser = tipo;
        this.campeonato_selecionado = campeonato;
        this.piloto_selecionado = piloto;
        this.carro_selecionado = carro;
    }

    public Utilizador(String nu, String pass, char tipo){
        this.nome_utilizador = nu;
        this.password = pass;
        this.tipoDeUser = tipo;
        this.campeonato_selecionado = new Campeonato();
        this.piloto_selecionado = new Piloto();
        this.carro_selecionado = new Carro();
    }

    public Utilizador(Utilizador u){
        this.nome_utilizador = u.getNome_utilizador();
        this.password = u.getPassword();
        this.tipoDeUser = u.getTipoDeUser();
        this.piloto_selecionado = u.getPiloto();
    }

    public String getNome_utilizador() {
        return this.nome_utilizador;
    }

    public void setNome_utilizador(String nome_utilizador) {
        this.nome_utilizador = nome_utilizador;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getTipoDeUser() {
        return this.tipoDeUser;
    }

    public void setTipoDeUser(char tipoDeUser) {
        this.tipoDeUser = tipoDeUser;
    }

    public Piloto getPiloto () {
        return this.piloto_selecionado;
    }

    public void setPiloto (Piloto p) {
        this.piloto_selecionado = p;
    }

    public Carro getCarro () {
        return this.carro_selecionado;
    }

    public void setCarro(Carro c) {
        this.carro_selecionado = c;
    }

    public Campeonato getCampeonato () {
        return this.campeonato_selecionado;
    }

    public void setCampeonato(Campeonato c) {
        this.campeonato_selecionado = c;
    }


}
//premium, normal e admin vao ser extends?
