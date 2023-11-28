package Devices;
import java.util.Objects;

public class SmartBulb extends SmartDevice
{
    // Variáveis de instância
    private final int COLD = 0;
    private final int NEUTRAL = 1;
    private final int WARM = 2;

    private int Tonalidade; /* Tonalidade da SmartBulb */
    private int Dimensao; /* Dimensao da SmartBulb em cms */

    // Construtores
    // Omissão
    public SmartBulb()
    {
        super();
        this.Tonalidade = NEUTRAL;
        this.Dimensao = 10;
        calculaConsumoEnergetico();
    }

    // Parametrizado
    public SmartBulb(boolean isOn, float consumoDiario, int ID, int tonalidade, int dimensao)
    {
        super(isOn, consumoDiario, ID);
        this.setTonalidade(tonalidade);
        this.setDimensao(dimensao);
        calculaConsumoEnergetico();
    }

    public SmartBulb(int id, int tonalidade, int dimensao, float consumoDiario)
    {
        super(false, consumoDiario, id);
        this.setTonalidade(tonalidade);
        this.setDimensao(dimensao);
        calculaConsumoEnergetico();
    }


    // Cópia
    public SmartBulb(SmartBulb smartBulb)
    {
        super(smartBulb.isOn(), smartBulb.getConsumoDiario(), smartBulb.getID());
        this.setTonalidade(smartBulb.getTonalidade());
        this.setDimensao(smartBulb.getDimensao());
        calculaConsumoEnergetico();
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters/Setters
    public int getTonalidade()
    {
        return this.Tonalidade;
    }

    public void setTonalidade(int tonalidade)
    {
        this.Tonalidade = tonalidade;
    }

    public int getDimensao() {
        return this.Dimensao;
    }

    public void setDimensao(int dimensao) {
        this.Dimensao = dimensao;
    }

    // Método que calcula o consumo energético de uma smartBulb
    // 15 = Valor fixo
    public void calculaConsumoEnergetico()
    {
        float aux = 0;
        if (this.Tonalidade == COLD)
        {
            aux = (float) (15 * 0.5);
            this.setConsumoEnergetico(aux);
        }
        else if (this.Tonalidade == NEUTRAL)
        {
            aux = (float) (15 * 1.15);
            this.setConsumoEnergetico(aux);
        }
        else
        {
            aux = (float) (15 * 1.5);
            this.setConsumoEnergetico(aux);
        }
    }

    // clone
    public SmartDevice clone()
    {
        return new SmartBulb(this);
    }

    // equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return this.getTonalidade() == smartBulb.getTonalidade() &&
                this.getDimensao() == smartBulb.getDimensao() &&
                Double.compare(smartBulb.getConsumoDiario(), this.getConsumoDiario()) == 0;
    }

    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("SmartBulb \n{\n");
        sb.append(super.toString());
        sb.append("\tTonalidade: " + this.getTonalidade() + "\n");
        sb.append("\tDimensao: " + this.getDimensao() + "\n}\n");
        return sb.toString();
    }
}
