package src.Carros;

public class C2H extends C2 implements Hibrido
{
    private int motor_eletrico;
    
    public C2H()
    {
        super();
        this.motor_eletrico = 0;
    }
    
    public C2H(int id,String marca, String modelo,float pac, float fiabilidade, int cilindrada, String pneus,int potencia,int afinacao,int eletrico)
    {
        super(id,marca,modelo,pac,fiabilidade,cilindrada,pneus,potencia,true,afinacao);
        this.motor_eletrico = eletrico;
    }
    
    public C2H(C2H p)
    {
        super(p);
        this.motor_eletrico = p.getPotenciaMotorEletrico();
    }
    
    public C2H clone()
    {
        return new C2H(this);
    }
    
    public int getPotenciaMotorEletrico()
    {
        return this.motor_eletrico;
    }
    
    public void setPotenciaMotorEletrico(int e)
    {
        this.motor_eletrico = e; 
    }
    
    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        C2H c = (C2H) o;
        return ( super.equals(c) && this.motor_eletrico == c.getPotenciaMotorEletrico());
    }   
}
