package Entidades;
import java.util.Objects;
import java.util.*;
public class Fornecedor
{
    // Variáveis de instância
    private String Nome; /* Nome do fornecedor de energia */
    private Double ValorBase; /* Valor Base do preço da energia */
    private final double Imposto  = 0.23;
    private Map<String, Casa> Casas;
    private double Faturacao;

    //Construtores
    // Omissão
    public Fornecedor()
    {
        this.Nome = "";
        this.ValorBase = 0.0;
        this.Casas = new HashMap<>();
        this.Faturacao = 0.0;
    }

    // Parametrizado
    public Fornecedor(String nome, double valorBase)
    {
        this.setNome(nome);
        this.setValorBase(valorBase);
        this.Casas = new HashMap<>();
        this.Faturacao = 0.0;
    }

    public Fornecedor(String nome, Double valorBase, Map<String, Casa> casas, double faturacao)
    {
        this.setNome(nome);
        this.setValorBase(valorBase);
        this.setCasas(casas);
        this.setFaturacao(faturacao);
    }

    // Cópia
    public Fornecedor(Fornecedor fornecedor)
    {
        this.setNome(fornecedor.getNome());
        this.setValorBase(fornecedor.getValorBase());
        this.setCasas(fornecedor.getCasas());
        this.setFaturacao(fornecedor.getFaturacao());
    }

    // Métodos de acesso e alteração de variáveis de instância
    //Getters e Setters
    public String getNome()
    {
        return this.Nome;
    }

    public void setNome(String nome)
    {
        this.Nome = nome;
    }

    public Map<String, Casa> getCasas()
    {
        Map<String, Casa> copy = new HashMap<>();
        for (Map.Entry<String, Casa> entry : this.Casas.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    public void setCasas(Map<String, Casa> casas)
    {
        this.Casas = new HashMap<>();
        for (Map.Entry<String, Casa> entry : casas.entrySet())
        {
            this.Casas.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public Double getValorBase()
    {
        return this.ValorBase;
    }

    public void setValorBase(Double valorBase)
    {
        this.ValorBase = valorBase;
    }

    public double getFaturacao()
    {
        return this.Faturacao;
    }

    public void setFaturacao(double faturacao)
    {
        this.Faturacao = faturacao;
    }

    //Code
    // Casa mais gastadora do fornecedor
    public Casa casaMaisGastadoraFornecedor()
    {
        Casa aux = this.getCasas().get(0);
        double maiorvalor = aux.consumoEnergeticoCasa();
        for ( Casa c1 : this.getCasas().values())
        {
            if (maiorvalor < c1.consumoEnergeticoCasa())
            {
                maiorvalor = c1.consumoEnergeticoCasa();
                aux = c1;
            }
        }
        return aux;
    }

    // Método que adiciona uma casa que começou a ser fornecida por um fornecedor
    public void addCasa(Casa casa)
    {
        this.Casas.put(casa.getNIF(), casa);
    }

    // Faturacao
    public double faturacao (double dias)
    {
        double precotot = 0;
        for (Casa c1 : this.getCasas().values())
        {
            precotot += c1.consumoTotalCasa(dias);
        }
        this.setFaturacao(precotot);
        return precotot;
    }

    //Lista das faturas
    public List<Fatura> listaDeFaturas ()
    {
        List<Fatura> lista = new ArrayList<>();
        for (Casa c1: this.getCasas().values())
        {
            lista.add(c1.getFatura());
        }
        return lista;
    }


    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Fornecedor \n{\n");
        sb.append("Empresa: " + this.getNome() + "\n");
        sb.append("Valor Base: " + this.getValorBase() + "\n");
        sb.append("Imposto: " + this.Imposto + "\n}\n");
        return sb.toString();
    }

    //equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor that = (Fornecedor) o;
        return Objects.equals(this.getNome(), that.getNome()) &&
                Objects.equals(this.getValorBase(), that.getValorBase());
    }

    //Hashcode
    @Override
    public int hashCode()
    {
        return Objects.hash(this.getValorBase());
    }

    //clone
    public Fornecedor clone()
    {
        return new Fornecedor(this);
    }

}