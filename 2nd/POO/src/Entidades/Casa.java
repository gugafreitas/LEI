package Entidades;

import java.util.*;
import Devices.*;


public class Casa
{
    // Variáveis de instância
    private String Proprietario; /* Nome do proprietário da casa */
    private String NIF; /* NIF do proprietário, usado como identificador */
    private Fornecedor Fornecedor; /* Nome do comercializador de energia */
    private Set<String> Divisoes; /* Lista com todas as divisões da casa */
    private Map<Integer, SmartDevice> Dispositivos; /* Map que contém todos os dispositivos na casa; key -> id dispositivo */
    private Map<String, List<SmartDevice>> DispositivosPorDivisao; /* String identifica a divisão; Lista com os dispositivos numa divisão */
    private Fatura Fatura; /* Classe que produz uma fatura do consumo da casa */

    // Construtores
    // Omissão
    public Casa()
    {
        this.Proprietario = "";
        this.NIF = "";
        this.Fornecedor = new Fornecedor();
        this.Divisoes = new HashSet<>();
        this.Dispositivos = new HashMap<>();
        this.DispositivosPorDivisao = new HashMap<>();
        this.Fatura = new Fatura();
    }

    // Parametrizado
    public Casa(String proprietario, String NIF, Fornecedor fornecedor, HashSet<String> divisoes,
                Map<Integer, SmartDevice> dispositivos, Map<String, List<SmartDevice>> dispositivosPorDivisao)
    {
        this.setProprietario(proprietario);
        this.setNIF(NIF);
        this.setFornecedor(fornecedor);
        this.setDivisoes(divisoes);
        this.setDispositivos(dispositivos);
        this.setDispositivosPorDivisao(dispositivosPorDivisao);
        this.Fatura = new Fatura();
    }

    public Casa(String proprietario, String NIF, Fornecedor fornecedor)
    {
        this.setProprietario(proprietario);
        this.setNIF(NIF);
        this.setFornecedor(fornecedor);
        this.Divisoes = new HashSet<>();
        this.Dispositivos = new HashMap<>();
        this.DispositivosPorDivisao = new HashMap<>();
        this.Fatura = new Fatura();
    }

    // Cópia
    public Casa(Casa casa)
    {
        this.setProprietario(casa.getProprietario());
        this.setNIF(casa.getNIF());
        this.setFornecedor(casa.getFornecedor());
        this.setDivisoes(casa.getDivisoes());
        this.setDispositivos(casa.getDispositivos());
        this.setDispositivosPorDivisao(casa.getDispositivosPorDivisao());
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters/Setters
    public String getProprietario()
    {
        return this.Proprietario;
    }

    public void setProprietario(String proprietario)
    {
        this.Proprietario = proprietario;
    }

    public String getNIF()
    {
        return this.NIF;
    }

    public void setNIF(String NIF)
    {
        this.NIF = NIF;
    }

    public Fornecedor getFornecedor()
    {
        return new Fornecedor(this.Fornecedor);
    }

    public void setFornecedor(Fornecedor fornecedor)
    {
        this.Fornecedor = new Fornecedor(fornecedor);
    }

    public Set<String> getDivisoes()
    {
        Set<String> copy = new HashSet<>();
        for (String s : this.Divisoes)
        {
            copy.add(s);
        }
        return copy;
    }

    public void setDivisoes(Set<String> divisoes)
    {
        this.Divisoes = new HashSet<>();
        for (String s : divisoes)
        {
            this.Divisoes.add(s);
        }
    }

    public Map<Integer, SmartDevice> getDispositivos()
    {
        Map<Integer, SmartDevice> copy = new HashMap<>();
        for (Map.Entry<Integer, SmartDevice> entry : this.Dispositivos.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    public void setDispositivos(Map<Integer, SmartDevice> dispositivos)
    {
        this.Dispositivos = new HashMap<>();
        for (Map.Entry<Integer, SmartDevice> entry : dispositivos.entrySet())
        {
            this.Dispositivos.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public Map<String, List<SmartDevice>> getDispositivosPorDivisao()
    {
        Map<String, List<SmartDevice>> copy = new HashMap<>();
        for (Map.Entry<String, List<SmartDevice>> entry : this.DispositivosPorDivisao.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    public void setDispositivosPorDivisao(Map<String, List<SmartDevice>> dispositivosPorDivisao)
    {
        this.DispositivosPorDivisao = new HashMap<>();
        for (Map.Entry<String, List<SmartDevice>> entry : dispositivosPorDivisao.entrySet())
        {
            this.DispositivosPorDivisao.put(entry.getKey(), entry.getValue());
        }
    }

    public Fatura getFatura()
    {
        return new Fatura(this.Fatura);
    }

    public void setFatura(Fatura fatura)
    {
        this.Fatura = new Fatura(fatura);
    }

    // Método que adiciona uma nova divisão à casa
    public void addDivisao(String divisao)
    {
        List<SmartDevice> aux = new ArrayList<>();
        this.Divisoes.add(divisao);
        this.DispositivosPorDivisao.put(divisao, aux);
    }

    // Método que adiciona um novo dispositivo a uma divisão
    public void addDispositivoDivisao(String divisao, SmartDevice smartDevice)
    {
        if (this.getDivisoes().contains(divisao))
        {
            this.DispositivosPorDivisao.get(divisao).add(smartDevice);
        }
        else
        {
            this.addDivisao(divisao);
            this.DispositivosPorDivisao.get(divisao).add(smartDevice);
        }
    }

    // Método que liga todos os dispositivos de uma divisão da casa
    public boolean turnONDivisao(String divisao)
    {
        if (this.getDivisoes().contains(divisao))
        {
            for (SmartDevice sd : this.DispositivosPorDivisao.get(divisao))
            {
                sd.setOn(true);
            }
            return true;
        }
        return false;
    }

    // Método que desliga todos os dispositivos de uma divisão da casa
    public boolean turnOFFDivisao(String divisao)
    {
        if (this.getDivisoes().contains(divisao))
        {
            for (SmartDevice sd : this.DispositivosPorDivisao.get(divisao))
            {
                sd.setOn(false);
            }
            return true;
        }
        return false;
    }

    // Método que liga um dispositivo especifico na casa
    public boolean turnONDispositivo(int id)
    {
        if (this.Dispositivos.containsKey(id))
        {
            this.Dispositivos.get(id).setOn(true);
            return true;
        }
        return false;
    }

    // Método que desliga um dispositivo especifico na casa
    public boolean turnOFFDispositivo(int id)
    {
        if (this.Dispositivos.containsKey(id))
        {
            this.Dispositivos.get(id).setOn(false);
            return true;
        }
        return false;
    }

    // Avançar o tempo, devolve o número de dias para fazer o calculo dos custos. Coloca data na fatura
    public double avancatempo(Date destino)
    {
        Date today = new Date();  
        today = Calendar.getInstance().getTime();
        long msDiff = destino.getTime() - today.getTime();

        double days = (msDiff*0.000000011574074);

        this.Fatura.setDiaInicial(today);
        this.Fatura.setDiaFinal(destino);

        return days;
    }

    // Método que calcula o consumo energetico numa divisão
    public double getConsumoDivisao(String divisao)
    {
        double consumoDivisao = 0;

        if (this.Dispositivos.containsKey(divisao))
        {
            for (SmartDevice smartDevice : this.DispositivosPorDivisao.get(divisao))
            {
                consumoDivisao += smartDevice.getConsumoDiario();
            }
        }

        return consumoDivisao;
    }

    // Método que devolve o consumo energético da casa
    public double consumoEnergeticoCasa ()
    {
        double consumoEnergetico = 0.0;
        for (String divisao : this.DispositivosPorDivisao.keySet())
        {
            consumoEnergetico += getConsumoDivisao(divisao);
        }
        return consumoEnergetico;
    }

    // Método que devolve o preço para a fatura e exportar para a fatura da respetiva casa
    public double consumoTotalCasa(double dias)
    {
        double consumo = consumoEnergeticoCasa();
        double preco;

        preco = dias * (this.Fornecedor.getValorBase() * consumo * (1 + 0.23)) * 0.9;

        this.Fatura.setPreco(preco);

        return preco;
    }

    // Método que devolve a fatura após determinados dias
    public Fatura faturaApos(Date destino)
    {
        double dias = avancatempo(destino);
        consumoTotalCasa(dias);

        return this.Fatura.clone();
    }


    // Método que devolve uma string com todos os dispositivos existentes na casa
    public String getListaDispositivos()
    {
        StringBuilder sb = new StringBuilder("Dispositivos por Divisão \n{\n");
        for (String s : this.DispositivosPorDivisao.keySet())
        {
            sb.append("Divisão: " + s + "\n");
            for (SmartDevice sd : this.getDispositivosPorDivisao().get(s))
            {
                sb.append(sd.toString());
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    // Método que determina quanto gastou a casa num determinado periodo de tempo
    public double getConsumoIntervalo(Date start, Date finish)
    {
        // getting milliseconds for both dates
        long startInMs = start.getTime();
        long finishInMs = finish.getTime();

        // getting the diff between two dates.
        long timeDiff = 0;
        if(startInMs > finishInMs)
        {
            timeDiff = startInMs - finishInMs;
        }
        else
        {
            timeDiff = finishInMs - startInMs;
        }

        // converting diff into days
        int daysDiff = (int) (timeDiff / (1000 * 60 * 60* 24));

        return this.getFornecedor().faturacao(daysDiff);
    }


    // clone
    public Casa clone()
    {
        return new Casa(this);
    }

    // equals
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return Objects.equals(this.getProprietario(), casa.getProprietario()) &&
                Objects.equals(this.getNIF(), casa.getNIF()) &&
                Objects.equals(this.getFornecedor(), casa.getFornecedor()) &&
                Objects.equals(this.getDivisoes(), casa.getDivisoes()) &&
                Objects.equals(this.getDispositivos(), casa.getDispositivos()) &&
                Objects.equals(this.getDispositivosPorDivisao(), casa.getDispositivosPorDivisao());
    }

    // toString
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Casa \n{\n");
        sb.append("\tProprietario: " + this.getProprietario() + "\n");
        sb.append("\tNIF: " + this.getNIF() + "\n");
        sb.append("\tDivisoes: " + this.getDivisoes() + "\n");
        sb.append("\tDispositivos: " + this.getDispositivos() + "\n");
        sb.append("\tLocalizações: " + this.getDispositivosPorDivisao() + "\n}\n");
        return sb.toString();
    }
}
