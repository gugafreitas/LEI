import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Utilizador {
    private String email;
    private String password;
    private String nome;
    private Genero genero;
    private double altura;
    private double peso;
    private LocalDate data_nascimento;
    private String desporto_favorito;

    /* Map de atividades. Podia ser uma lista e mantinha a ordem..*/
    private Map<String, Atividades> atividades;

    public Utilizador(){
        this.email = "";
        this.password = "";
        this.nome = "nome";
        this.genero = Genero.Outro;
        this.altura = 0;
        this.peso = 0;
        this.data_nascimento = LocalDate.EPOCH;
        this.desporto_favorito = "";
        this.atividades = new HashMap<>();
    }
    public Utilizador(String email, String password, String nome, Genero genero, double altura,
                      double peso, LocalDate data_nascimento, String desporto_favorito,
                      Map<String, Atividades> atividades) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.data_nascimento = data_nascimento;
        this.desporto_favorito = desporto_favorito;
        this.atividades = atividades.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Utilizador(String email, String password, String nome, Genero genero, double altura,
                      double peso, LocalDate data_nascimento, String desporto_favorito) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.genero = genero;
        this.altura = altura;
        this.peso = peso;
        this.data_nascimento = data_nascimento;
        this.desporto_favorito = desporto_favorito;
        this.atividades = new HashMap<>();
    }
    public Utilizador(Utilizador outro){
        this.email = outro.getEmail();
        this.password = outro.getPassword();
        this.nome = outro.getNome();
        this.genero = outro.getGenero();
        this.altura = outro.getAltura();
        this.peso = outro.getAltura();
        this.data_nascimento = outro.getData_nascimento();
        this.desporto_favorito = outro.getDesporto_favorito();
        this.atividades = outro.getAtividades();
    }

    public Map<String, Atividades> getAtividades() {
        return this.atividades.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setAtividades(Map<String, Atividades> atividades) {
        this.atividades =  atividades.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public LocalDate getData_nascimento() {
        return this.data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getDesporto_favorito() {
        return this.desporto_favorito;
    }

    public void setDesporto_favorito(String desporto_favorito) {
        this.desporto_favorito = desporto_favorito;
    }

    public void addAtividade(Atividades a){
        this.atividades.put(a.getId(), a.clone());
    }

    public Atividades getAtividade(String cod){
        if(!atividades.containsKey(cod)){
            return null;
        }
        return this.atividades.get(cod).clone();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return Double.compare(that.getAltura(), getAltura()) == 0
                && Double.compare(that.getPeso(), getPeso()) == 0
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getNome(), that.getNome())
                && getGenero() == that.getGenero()
                && Objects.equals(getData_nascimento(), that.getData_nascimento())
                && Objects.equals(getDesporto_favorito(), that.getDesporto_favorito())
                && this.atividades.equals(that.getAtividades());
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", genero=" + genero +
                ", altura=" + altura +
                ", peso=" + peso +
                ", data_nascimento=" + data_nascimento +
                ", desporto_favorito=" + desporto_favorito +
                '}';
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }

    /*public boolean existeUtilizador(String email){
        List<String> emails = new ArrayList<>();
    }

    public int quantos(){
    }*/


}