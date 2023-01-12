package src.Carros;

public class C1 extends Carro{

    public C1(){
        super();
    }

	public C1(int id,String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido){
        super(id,marca, modelo, pac, fiabilidade, cilindrada, pneus, potencia, hibrido);
    }

    public C1(C1 p){
        super(p);
    }

    @Override
    public C1 clone(){
        return new C1(this);
    }

    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        C1 c = (C1) o;
        return ( super.equals(c));
    }
}