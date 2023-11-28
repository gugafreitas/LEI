package Entidades;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Fatura
{
    // Variáveis de instância
    private double Preco; /* Preço final da fatura */
    private Date DiaInicial; /* Dia Inicial do período de faturação */
    private Date DiaFinal; /* Dia final do período de faturação */

    // Construtores
    // Omissão
    public Fatura()
    {
        this.Preco = 0.0;
        this.DiaInicial = new Date(2022, 05, 12);
        this.DiaFinal = new Date(2022, 06, 12);
    }

    // Parametrizado
    public Fatura(double preco, Date diaInicial, Date diaFinal)
    {
        this.setPreco(preco);
        this.setDiaInicial(diaInicial);
        this.setDiaFinal(diaFinal);
    }

    public Fatura(Fatura fatura)
    {
        this.setPreco(fatura.getPreco());
        this.setDiaInicial(fatura.getDiaInicial());
        this.setDiaFinal(fatura.getDiaFinal());
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters e setters
    public double getPreco()
    {
        return this.Preco;
    }

    public void setPreco(double preco)
    {
        if (preco >= 0)
        {
            this.Preco = preco;
        }
        else
        {
            this.Preco = 0;
        }
    }

    public Date getDiaInicial()
    {
        return this.DiaInicial;
    }

    public void setDiaInicial(Date diaInicial)
    {
        this.DiaInicial = diaInicial;
    }

    public Date getDiaFinal()
    {
        return this.DiaFinal;
    }

    public void setDiaFinal(Date diaFinal)
    {
        this.DiaFinal = diaFinal;
    }

    //equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fatura fatura = (Fatura) o;
        return Double.compare(fatura.getPreco(), this.getPreco()) == 0 &&
                Objects.equals(fatura.getDiaInicial(), this.getDiaInicial()) &&
                Objects.equals(fatura.getDiaFinal(), this.getDiaFinal());
    }

    //hash code
    public int hashCode()
    {
        return Objects.hash(this.getPreco(), this.getDiaInicial(), this.getDiaFinal());
    }

    // clone
    public Fatura clone()
    {
        return new Fatura(this);
    }

    //toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Fatura \n{\n");
        sb.append("\tPreço: " + this.getPreco() + "\n");
        sb.append("\tDia Inicial: " + this.getDiaInicial() + "\n");
        sb.append("\tDia Final: " + this.getDiaFinal() + "\n}\n");
        return sb.toString();
    }
}
