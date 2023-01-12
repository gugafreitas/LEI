package src.Pilotos;

public class Piloto {

    private float CTS;
    private float SVA;
    private boolean available;
    private String nome;

    public Piloto(){
        this.CTS = 0; //entre 0 e 1
        this.SVA = 0;//entre 0 e 1
        this.available = false;
        this.nome = "";
    }

    public Piloto(String nome, boolean available,float CTS, float SVA){
        this.CTS = CTS;
        this.SVA = SVA;
        this.available = available;
        this.nome = nome;
    }

    public Piloto(Piloto p){
        this.CTS = p.getCTS();
        this.SVA = p.getSVA();
        this.available = p.getAvailable();
        this.nome = p.getNome();
    }

    public float getCTS() {
        return CTS;
    }

    public void setCTS(float CTS) {
        this.CTS = CTS;
    }

    public float getSVA() {
        return SVA;
    }

    public void setSVA(float SVA) {
        this.SVA = SVA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean getAvailable(){
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean validateCTS(){
        if(this.getCTS()>=0 && this.getCTS()<=1) return true;
        else return false;
    }

    public boolean validateSVA(){
        if(this.getSVA()>=0 && this.getSVA()<=1) return true;
        else return false;
    }
}
