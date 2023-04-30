package Utilizadores;

import java.util.Date;

public class Utilizador {
    private String email;
    private String password;
    private String nome;
    private String género;
    private int altura;
    private float peso;
    private Date dataNascimento;
    private Date dataRegistoSistema;

    public Utilizador(String email, String password, String nome, String género, int altura, float peso, Date dataNascimento, Date dataRegistoSistema) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.género = género;
        this.altura = altura;
        this.peso = peso;
        this.dataNascimento = dataNascimento;
        this.dataRegistoSistema = dataRegistoSistema;
    }
}
