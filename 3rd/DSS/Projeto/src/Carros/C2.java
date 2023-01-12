package src.Carros;

public class C2 extends Carro{

    private int afinacao;

    public C2(){
        super();
    }

	public C2(int id,String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido,int afinacao){
        super(id,marca, modelo, pac, fiabilidade, cilindrada, pneus, potencia, hibrido);
        this.afinacao = afinacao;
    }

    public C2(C2 p){
        super(p);
        this.afinacao = p.getAfinacao();
    }

    public int getAfinacao(){
        return this.afinacao;
    }

    @Override
    public C2 clone(){
        return new C2(this);
    }

    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        C2 c = (C2) o;
        return ( super.equals(c) && this.afinacao == c.getAfinacao());
    }
}