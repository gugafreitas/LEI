package Devices;

import java.util.Objects;

public class SmartCamera extends SmartDevice
{
    // Variáveis de instância
    private String Resolucao; /* Resolução de uma SmartCamera */
    private float Dimensao; /* Dimensão de uma SmartCamera */

    // Construtores
    // Omissão
    public SmartCamera()
    {
        super();
        this.Resolucao = "";
        this.Dimensao = 10;
        calculaConsumoEnergetico();
    }

    // Parametrizado
    public SmartCamera(boolean isOn, float consumoDiario, int ID, String resolucao, float dimensao)
    {
        super(isOn, consumoDiario, ID);
        this.setResolucao(resolucao);
        this.setDimensao(dimensao);
        calculaConsumoEnergetico();
    }

    public SmartCamera(int id, float dimensao, String resolucao, float consumoDiario)
    {
        super(false, consumoDiario, id);
        this.setResolucao(resolucao);
        this.setDimensao(dimensao);
        calculaConsumoEnergetico();
    }

    // Copia
    public SmartCamera(SmartCamera smartCamera)
    {
        super(smartCamera.isOn(), smartCamera.getConsumoDiario(), smartCamera.getID());
        this.setResolucao(smartCamera.getResolucao());
        this.setDimensao(smartCamera.getDimensao());
        calculaConsumoEnergetico();
    }

    // Métodos de acesso e alteração de variáveis de instÂncia
    // getters/setters
    public String getResolucao()
    {
        return this.Resolucao;
    }

    public void setResolucao(String resolucao)
    {
        this.Resolucao = resolucao;
    }

    public float getDimensao()
    {
        return this.Dimensao;
    }

    public void setDimensao(float dimensao)
    {
        this.Dimensao = dimensao;
    }

    // Método que calcula o consumo energético de uma smartspeaker
    public void calculaConsumoEnergetico()
    {
        float aux = (float)(this.getDimensao() * 1.6);
        this.setConsumoEnergetico(aux);
    }

    // clone
    public SmartDevice clone()
    {
        return new SmartCamera(this);
    }

    // equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return this.getResolucao() == that.getResolucao() &&
                Float.compare(that.getDimensao(), this.getDimensao()) == 0;
    }

    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("SmartCamera \n{\n");
        sb.append(super.toString());
        sb.append("\tResolucao: " + this.getResolucao() + "\n");
        sb.append("\tDimensao: " + this.getDimensao() + "\n}\n");
        return sb.toString();
    }
}
