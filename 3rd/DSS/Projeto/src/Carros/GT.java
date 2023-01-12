package src.Carros;

public class GT extends Carro{

    public GT(){
        super();
    }

    public GT(int id,String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido){
        super(id,marca, modelo, pac, fiabilidade, cilindrada, pneus, potencia, hibrido);
    }

    public GT(GT p){
        super(p);
    }

    @Override
    public GT clone(){
        return new GT(this);
    }

    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        GT c = (GT) o;
        return ( super.equals(c));
    }
}