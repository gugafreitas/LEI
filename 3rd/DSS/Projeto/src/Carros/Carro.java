package src.Carros;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private float pac;
    private float fiabilidade;
    private int cilindrada;
    private String pneus;
    private int potencia;
    private boolean hibrido;

    public Carro(){
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.pac = 0;
        this.fiabilidade = 0;
        this.cilindrada = 0;
        this.pneus = "";
        this.potencia = 0;
        this.hibrido = false;
    }

    public Carro(int id, String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.pac = pac;
        this.fiabilidade = fiabilidade;
        this.cilindrada = cilindrada;
        this.pneus = pneus;
        this.potencia = potencia;
        this.hibrido = hibrido;
    }

    public Carro(Carro c){
        this.id = c.getID();
        this.marca = c.getMarca();
        this.modelo = c.getModelo();
        this.pac = c.getPAC();
        this.fiabilidade = c.getFiabilidade();
        this.cilindrada = c.getCilindrada();
        this.pneus = c.getPneus();
        this.potencia = c.getPotencia();
        this.hibrido = c.getHibrido();
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public float getPAC() {
        return pac;
    }

    public void setPAC(float pac) {
        this.pac = pac;
    }

    public float getFiabilidade() {
        return fiabilidade;
    }

    public void setFiabilidade(float fiabilidade) {
        this.fiabilidade = fiabilidade;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public String getPneus() {
        return this.pneus;
    }

    public void setPneus(String pneus) {
        this.pneus = pneus;
    }

    public int getPotencia() {
        return this.potencia;
    }

    public void setPotencia(int pot) {
        this.potencia = pot;
    }

    public boolean getHibrido(){
        return this.hibrido;
    }

    public void setHibirdo(boolean hibrido){
        this.hibrido = hibrido;
    }

    public float checkFia(){
        return this.fiabilidade;
    }




    public boolean checkFia95(){
        return (this.fiabilidade > 0.9 && this.fiabilidade <= 0.95);
    }

    public boolean checkFia80(){
        return (this.fiabilidade > 0.75 && this.fiabilidade < 0.80);
    }
    /*
    public void regCategory(String cat){
        if(this.hibrido){
            if(cat == "C1"){

            }
            if(cat == "C2"){
            
            }
            if(cat == "GT"){
            
            }            
        }
        else{
            if(cat == "SC"){
            
            }
            if(cat == "C1"){

            }
            if(cat == "C2"){
            
            }
            if(cat == "GT"){
            
            }  
        }       
    }*/

}
