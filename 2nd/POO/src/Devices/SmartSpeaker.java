package Devices;
import java.util.Objects;
public class SmartSpeaker extends SmartDevice
{
    // Variáveis de instância
    private static final int MAX = 25; /* Volume máximo de uma SmartSpeaker */
    private int Volume; /* Volume de uma coluna */
    private String Radio; /* Radio que está a tocar na smartspeaker */
    private String Marca; /* Marca da coluna */

    // Construtores
    // Omissão
    public SmartSpeaker()
    {
        super();
        this.Volume = 10;
        this.Radio = "";
        this.Marca = "";
    }

    // Parametrizado
    public SmartSpeaker(boolean isOn, float consumoDiario, int ID, int volume, String radio, String marca)
    {
        super(isOn, consumoDiario, ID);
        this.setVolume(volume);
        this.setRadio(radio);
        this.setMarca(marca);
    }

    public SmartSpeaker(int id, int volume, String radio, String marca, float consumoDiario)
    {
        super(false, consumoDiario, id);
        this.setVolume(volume);
        this.setRadio(radio);
        this.setMarca(marca);
    }


    // Cópia
    public SmartSpeaker(SmartSpeaker smartSpeaker)
    {
        super(smartSpeaker.isOn(), smartSpeaker.getConsumoDiario(), smartSpeaker.getID());
        this.setVolume(smartSpeaker.getVolume());
        this.setRadio(smartSpeaker.getRadio());
        this.setMarca(smartSpeaker.getMarca());
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters/Setters
    public int getVolume()
    {
        return this.Volume;
    }

    public void setVolume(int volume)
    {
        if (volume >= 0 && volume <= MAX)
        {
            this.Volume = volume;
        }
        else
        {
            this.Volume = 0;
        }
    }

    public String getRadio()
    {
        return this.Radio;
    }

    public void setRadio(String radio) {
        this.Radio = radio;
    }

    public String getMarca() {
        return this.Marca;
    }

    public void setMarca(String marca) {
        this.Marca = marca;
    }

    // Método que calcula o consumo energético de uma coluna
    public void calculaConsumoEnergetico()
    {
        float aux = 0;
        if (this.Volume < 10)
        {
            aux = (float) (10 * 0.75);
            this.setConsumoEnergetico(aux);
        }
        else if (this.Volume >= 10 && this.Volume < 20)
        {
            aux = (float) (10 * 1.3);
            this.setConsumoEnergetico(aux);
        }
        else
        {
            aux = (float) (10 * 1.63);
            this.setConsumoEnergetico(aux);
        }

    }

    // clone
    public SmartDevice clone()
    {
        return new SmartSpeaker(this);
    }

    // equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartSpeaker that = (SmartSpeaker) o;
        return this.getVolume() == that.getVolume() &&
                this.getRadio() == that.getRadio() &&
                Objects.equals(this.getMarca(), that.getMarca());
    }

    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("SmartSpeaker \n{\n");
        sb.append(super.toString());
        sb.append("\tMarca: " + this.getMarca() + "\n");
        sb.append("\tRadio: " + this.getRadio() + "\n");
        sb.append("\tVolume: " + this.getVolume() + "\n}\n");
        return sb.toString();
    }
}