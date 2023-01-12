package src.Carros;

public class C1H extends C1 implements Hibrido
{
    private int motor_eletrico;
    
    public C1H()
    {
       super();
       this.motor_eletrico = 0;
    }
    
    public C1H(int id,String marca, String modelo, float pac, float fiabilidade, int cilindrada, String pneus,int potencia,boolean hibrido)
    {
        super(id,marca,modelo,pac,fiabilidade,cilindrada,pneus,potencia,true);
        this.motor_eletrico = 0;
    }
    
    public C1H(C1H p)
    {
        super(p);
        this.motor_eletrico = p.getPotenciaMotorEletrico();
    }
    
    public C1H clone()
    {
        return new C1H(this);
    }
    
    public int getPotenciaMotorEletrico()
    {
        return this.motor_eletrico;
    }
    
    public void setPotenciaMotorEletrico(int potencia)
    {
        this.motor_eletrico = potencia;
    }
    
    // public boolean DNF(int volta,int totalvoltas,int clima)
    // {
    //    Random rand=new Random();
    //    int x=rand.nextInt(85);
    //    int motorh = this.getPotenciaMotorEletrico()/20;     
    //    return (x > (super.getFiabilidade()-motorh));
    //    //return false;
    // }
    
    public boolean equals(Object o)
    {
        if(this==o)
        return true;
        
        if(o==null || this.getClass()!=o.getClass())
        return false;
        
        C1H c = (C1H) o;
        return ( super.equals(c) && this.motor_eletrico == c.getPotenciaMotorEletrico());
    }    
}