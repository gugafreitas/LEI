package src.Carros;

public class SC extends Carro
{
    public SC()
    {
        super();
    }
    
    public SC(int id, String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido)
    {
        super(id, marca, modelo, pac, fiabilidade, cilindrada, pneus, potencia, hibrido);
    }
    
    public SC(SC p)
    {
        super(p);
    }
    
    @Override
    public SC clone()
    {
        return new SC(this);
    }    
     
    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        SC c = (SC) o;
        return ( super.equals(c));
    }
}
