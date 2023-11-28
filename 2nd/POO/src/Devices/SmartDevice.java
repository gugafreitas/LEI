package Devices;

import java.util.Objects;
public abstract class SmartDevice
{
    // Variáveis de instância
    private boolean isOn; /* Estado do dispositivo */
    private double ConsumoEnergetico; /* Consumo energético do dispositivo */
    private double ConsumoDiario; /* Consumo diario do dispositivo */
    private int ID; /* Identificado do dispositivo */
    private int NextID = 0;

    // Custo de instalação do dispositivo
    private final int CustoInstalacao = 25;

    // Construtores
    // Omissão
    public SmartDevice()
    {
        this.isOn = false;
        this.ConsumoDiario = 0;
        this.ID = this.NextID;
        this.NextID++;
    }

    // Parametrizado
    public SmartDevice(boolean isOn, double consumoDiario, int ID)
    {
        this.setOn(isOn);
        this.setConsumoDiario(consumoDiario);
        this.setID(ID);
    }

    // Cópia
    public SmartDevice(SmartDevice smartDevice)
    {
        this.setOn(smartDevice.isOn());
        this.setConsumoDiario(smartDevice.getConsumoDiario());
        this.setID(smartDevice.getID());
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters/Setters
    public boolean isOn()
    {
        return this.isOn;
    }

    public void setOn(boolean on)
    {
        this.isOn = on;
    }

    public double getConsumoEnergetico()
    {
        return this.ConsumoEnergetico;
    }

    public void setConsumoEnergetico(double consumoEnergetico)
    {
        this.ConsumoEnergetico = consumoEnergetico;
    }

    public double getConsumoDiario()
    {
        return this.ConsumoDiario;
    }

    public void setConsumoDiario(double consumoDiario)
    {
        this.ConsumoDiario = consumoDiario;
    }

    public int getID()
    {
        return this.ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getNextID()
    {
        return this.NextID;
    }

    public void setNextID(int nextID)
    {
        this.NextID = nextID;
    }

    public int getCustoInstalacao()
    {
        return this.CustoInstalacao;
    }

    // Método que calcula o consumo energetico de um dispositivo
    public abstract void calculaConsumoEnergetico();

    // clone
    public abstract SmartDevice clone();

    // equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        SmartDevice that = (SmartDevice) o;
        return this.isOn() == that.isOn() &&
                Double.compare(that.getConsumoEnergetico(), this.getConsumoEnergetico()) == 0 &&
                this.getID() == that.getID() &&
                this.getNextID() == that.getNextID();
    }

    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\tID: " + this.getID() + "\n");
        sb.append("\tLigado: " + this.isOn() + "\n");
        sb.append("\tConsumo Energetico: " + this.getConsumoEnergetico() + "\n");
        sb.append("\tConsumo Diario: " + this.getConsumoDiario() + "\n");
        return sb.toString();
    }
}
