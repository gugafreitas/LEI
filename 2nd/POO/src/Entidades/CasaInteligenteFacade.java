package Entidades;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import Devices.*;


public class CasaInteligenteFacade implements Serializable
{
    // Variáveis de instância
    private Map<String, Fornecedor> Fornecedores; /* Lista que contem os fornecedores de energia existentes */
    private Map<String, Casa> MapCasas; /* Map que contem todas as casas existentes */
    private Map<Integer, SmartDevice> MapDevices; /* Map que contem todos os dispositivos existentes */

    // Construtores
    // Omissão
    public CasaInteligenteFacade()
    {
        this.Fornecedores = new HashMap<>();
        this.MapCasas = new HashMap<String, Casa>();
        this.MapDevices = new HashMap<Integer, SmartDevice>();
    }

    // Cópia
    public CasaInteligenteFacade(CasaInteligenteFacade facade)
    {
        this.setFornecedores(facade.getFornecedores());
        this.setMapCasas(facade.getMapCasas());
        this.setMapDevices(facade.getMapDevices());
    }

    // Métodos de acesso e alteração de variáveis de instância
    // Getters/Setters
    public Map<String, Fornecedor> getFornecedores()
    {
        Map<String, Fornecedor> copy = new HashMap<>();
        for (Map.Entry<String, Fornecedor> entry : this.Fornecedores.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue());
        }
        return copy;
    }

    public void setFornecedores(Map<String, Fornecedor> fornecedores)
    {
        this.Fornecedores = new HashMap<>();
        for (Map.Entry<String, Fornecedor> entry : fornecedores.entrySet())
        {
            this.Fornecedores.put(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, Casa> getMapCasas()
    {
        Map<String, Casa> copy = new HashMap<>();
        for (Map.Entry<String, Casa> entry : this.MapCasas.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    public void setMapCasas(Map<String, Casa> mapCasas)
    {
        this.MapCasas = new HashMap<>();
        for (Map.Entry<String, Casa> entry : mapCasas.entrySet())
        {
            this.MapCasas.put(entry.getKey(), entry.getValue().clone());
        }
    }

    public Map<Integer, SmartDevice> getMapDevices()
    {
        Map<Integer, SmartDevice> copy = new HashMap<>();
        for (Map.Entry<Integer, SmartDevice> entry : this.MapDevices.entrySet())
        {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    public void setMapDevices(Map<Integer, SmartDevice> mapDevices)
    {
        this.MapDevices = new HashMap<>();
        for (Map.Entry<Integer, SmartDevice> entry : mapDevices.entrySet())
        {
            this.MapDevices.put(entry.getKey(), entry.getValue().clone());
        }
    }

    // Métodos relativos ao funcionamento do programa
    // Método que verifica se uma casa pertence ao programa
    public boolean checkCasa(String nif)
    {
        return this.getMapCasas().containsKey(nif);
    }

    // Método adiciona uma casa ao programa
    public void adicionaCasa(Casa c)
    {
        this.MapCasas.put(c.getNIF(), c);
    }

    // Método que adiciona um smartDevice ao programa
    public void adicionaDispositivo(SmartDevice smartDevice)
    {
        this.MapDevices.put(smartDevice.getID(), smartDevice);
    }

    // Método que adiciona um fornecedor ao programa
    public void adicionaFornecedor(Fornecedor fornecedor)
    {
        this.Fornecedores.put(fornecedor.getNome(), fornecedor);
    }

    // Método que devolve uma string com os nomes do fornecedores
    public String listaForncedores()
    {
        StringBuilder sb = new StringBuilder("Lista Forncedores \n{\n");
        for (String s : this.getFornecedores().keySet())
        {
            sb.append("\tEmpresa: " + s + "\n");
        }
        sb.append("\n}\n");
        return sb.toString();
    }

    // Método que devolve uma string com os nifs + nome de proprietários das casas
    public String listaCasa()
    {
        StringBuilder sb = new StringBuilder("Lista Casas \n{\n");
        for (Casa c : this.getMapCasas().values())
        {
            sb.append("\tNIF: " + c.getNIF() + "; Proprietário: " + c.getProprietario() + "\n");
        }
        sb.append("\n}\n");
        return sb.toString();
    }

    // Método que devolve uma string com os ids do devices + tipo de device
    public String listaDevices()
    {
        StringBuilder sb = new StringBuilder("Lista Device \n{\n");
        for (SmartDevice smartDevice : this.getMapDevices().values())
        {
            sb.append("\tID: " + smartDevice.getID() + "; Tipo: " + smartDevice.getClass().getName() + "\n");
        }
        sb.append("\n}\n");
        return sb.toString();
    }

    // Método que liga todos os dispositivos de uma divisão de uma casa
    public boolean ligaDispositivoDivisao(String nif, String divisao)
    {
        if (this.getMapCasas().containsKey(nif))
        {
            return this.MapCasas.get(nif).turnONDivisao(divisao);
        }
        return false;
    }

    // Método que desliga todos os dispositivos de uma divisão de uma casa
    public boolean desligaDispositivoDivisao(String nif, String divisao)
    {
        if (this.getMapCasas().containsKey(nif))
        {
            return this.MapCasas.get(nif).turnOFFDivisao(divisao);
        }
        return false;
    }

    // Método que liga um dispositivo especifico de uma casa
    public boolean ligaDispositivo(String nif, int id)
    {
        if (this.getMapCasas().containsKey(nif))
        {
            this.MapDevices.get(id).setOn(true);
            return this.MapCasas.get(nif).turnONDispositivo(id);
        }
        return false;
    }

    // Método que liga um dispositivo especifico de uma casa
    public boolean desligaDispositivo(String nif, int id)
    {
        if (this.getMapCasas().containsKey(nif))
        {
            this.MapDevices.get(id).setOn(false);
            return this.MapCasas.get(nif).turnOFFDispositivo(id);
        }
        return false;
    }

    // Método que verifica que devolve a lista de fatura de um fornecedor
    public List<Fatura> getFaturasFornecedor(String fornecedor)
    {
        if (this.getFornecedores().containsKey(fornecedor))
        {
            return this.getFornecedores().get(fornecedor).listaDeFaturas();
        }
        return null;
    }

    // Método que devolve uma string com a informaão relativa a uma casa
    public String getCasa(String nif)
    {
        if (this.getMapCasas().containsKey(nif))
        {
            return this.getMapCasas().get(nif).toString();
        }
        return null;
    }

    // Método que devolve uma string com a informaão relativa a um dispositivo
    public String getSmartDevice(int id)
    {
        if (this.getMapDevices().containsKey(id))
        {
            return this.getMapDevices().get(id).toString();
        }
        return null;
    }

    // Método que devolve uma string com todos os dispositivo
    public String getDispositivos(String nif)
    {
        if (checkCasa(nif))
        {
            return this.getMapCasas().get(nif).getListaDispositivos();
        }
        return null;
    }

    // Método que adiciona um dispositivo a uma casa, numa divisão
    public void addDispositivoCasa(String nif, String divisao, SmartDevice smartDevice)
    {
        if (checkCasa(nif))
        {
            this.MapCasas.get(nif).addDispositivoDivisao(divisao, smartDevice);
        }
    }

    // Listar todas as faturas
    public List<Fatura> todasfaturas () {
        List <Fatura> todas= new ArrayList<>();
        for (Casa c : this.MapCasas.values()) {
            todas.add(c.getFatura());
        }
        return todas;
    }

    // dar uma ordenacao dos maiores consumidores de energia durante um perıodo a determinar
    public void swap(int i, int j, List<Casa> casas)
    {
        Casa tmp = casas.get(i);
        casas.set(i,casas.get(j));
        casas.set(j,tmp);
    }

    // Método que devolve uma lista ordenada com os maiores consumidores de energia
    public List<Casa> maioresConsumidores(Date inicio, Date fim)
    {
        List<Casa> maioresconsumidoras = new ArrayList<>();
        for (Casa c1 : this.MapCasas.values())
        {
            if (c1.getFatura().getDiaFinal().compareTo(fim) <= 0 && c1.getFatura().getDiaInicial().compareTo(inicio) >= 0)
            {
                maioresconsumidoras.add(c1);
            }
        }
        for(int i = 0; i < maioresconsumidoras.size() - 1; i++)
        {
            for(int j = 1; j < maioresconsumidoras.size(); j++)
            {
                if((maioresconsumidoras.get(j)).consumoEnergeticoCasa() < (maioresconsumidoras.get(i)).consumoEnergeticoCasa())
                {
                    swap(i, j, maioresconsumidoras);
                }
            }
        }
        return maioresconsumidoras;
    }

    public Casa maiorConsumidor(Date inicio, Date fim)
    {
        List<Casa> maioresconsumidoras = new ArrayList<>();
        for (Casa c1 : this.getMapCasas().values())
        {
            if (c1.getFatura().getDiaFinal().compareTo(fim) <= 0 && c1.getFatura().getDiaInicial().compareTo(inicio) >= 0)
            {
                maioresconsumidoras.add(c1);
            }
        }
        for(int i = 0; i < maioresconsumidoras.size() - 1; i++)
        {
            for(int j = 1; j < maioresconsumidoras.size(); j++)
            {
                if((maioresconsumidoras.get(j)).consumoEnergeticoCasa() < (maioresconsumidoras.get(i)).consumoEnergeticoCasa())
                {
                    swap(i, j, maioresconsumidoras);
                }
            }
        }
        return maioresconsumidoras.get(0);
    }

    // Fornecedor com maior faturacao
    public Fornecedor maiorfaturacao ()
    {
        double faturacao_maior = -100;
        Fornecedor fat = null;
        for (Fornecedor maiorFatura : this.getFornecedores().values())
        {
            if(maiorFatura.getFaturacao() > faturacao_maior)
            {
                fat = new Fornecedor(maiorFatura);
                faturacao_maior = maiorFatura.getFaturacao();
            }
        }
        return fat;
    }

    //Casa mais gastadora cidade
    public Casa maisgastadora()
    {
        List<Casa> casas_mais_gastadora_for = this.getFornecedores().values().stream().map(Fornecedor::casaMaisGastadoraFornecedor)
                .collect(Collectors.toList());
        Casa mais_gastadora = casas_mais_gastadora_for.get(0);
        for(Casa casas:casas_mais_gastadora_for)
        {
            if(casas.consumoEnergeticoCasa() > mais_gastadora.consumoEnergeticoCasa())
            {
                mais_gastadora = casas;
            }
        }
        return mais_gastadora;
    }

    // Método que devolve a lista de faturas de um comercializador
    public List<Fatura> getFaturasForncedor(String f)
    {
        if (this.getFornecedores().containsKey(f))
        {
            List<Fatura> res = this.getFornecedores().get(f).listaDeFaturas();
            return res;
        }
        return null;
    }

    public String listaFatura(String faturas)
    {
        List<Fatura> res = getFaturasFornecedor(faturas);
        StringBuilder sb = new StringBuilder("Faturas \n{\n");
        for (Fatura f : res)
        {
            sb.append(f.toString());
        }
        sb.append("\n}\n");
        return sb.toString();
    }

    /*
    // Método que atualiza a lista de casas de cada fornecedor
    public void atualizaCasasFornecedores()
    {
        for (Casa c : this.getMapCasas().values())
        {
            String f = c.getFornecedor().getNome();
            this.Fornecedores.get(f).addCasa(c);
        }
    }*/

    public void saveEstado() throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Estado.obj"));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    //carregar de ficheiro objeto
    public void loadEstadoObj(String file) throws IOException, ClassNotFoundException
    {
        CasaInteligenteFacade e = loadAux(file);
        this.Fornecedores = e.Fornecedores;
        this.MapCasas = e.MapCasas;
        this.MapDevices = e.MapDevices;
    }
    public CasaInteligenteFacade loadAux(String file) throws IOException, ClassNotFoundException
    {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        CasaInteligenteFacade e = (CasaInteligenteFacade) ois.readObject();
        ois.close();
        return e;
    }

    /*--------------------FUNCOES QUE ESTAVAM NA PARSE-----------------------*/
    // Método que faz o parse de um fornecedor
    public Fornecedor parseFornecedor(String input)
    {
        String[] campos = input.split(",");

        String nome = campos[0];
        String valorBase = campos[1];

        Fornecedor fornecedor = new Fornecedor(nome, Double.parseDouble(valorBase));
        this.Fornecedores.put(nome, fornecedor);
        return fornecedor;
    }

    // Método que faz o parse de uma casa
    public Casa parseCasa(String input)
    {
        String[] campos = input.split(",");
        String nome = campos[0];
        String nif = campos[1];
        String fornecedor = campos[2];

        if (this.getFornecedores().containsKey(fornecedor))
        {
            Fornecedor f = this.Fornecedores.get(fornecedor);
            Casa c = new Casa(nome, nif, f);
            this.MapCasas.put(nif, c);
            return c;
        }
        else
        {
            Fornecedor f = new Fornecedor(fornecedor, 1.2);
            Casa c = new Casa(nome, nif, f);
            this.Fornecedores.put(fornecedor, f);
            this.MapCasas.put(nif, c);
            return c;
        }
    }

    // Método que faz o parse de uma divisao
    public String parseDivisao(String input)
    {
        String[] campos = input.split(",");
        return campos[0];
    }

    // Método que faz o parse de uma smartbulb
    public SmartBulb parseSmartBulb(String input)
    {
        String[] campos = input.split(",");
        String tonalidade = campos[0];
        String dimensao = campos[1];
        String consumoDiario = campos[2];
        int aux = this.MapDevices.size();

        if (tonalidade.equals("Neutral"))
        {
            SmartBulb bulb = new SmartBulb(aux, 0, Integer.parseInt(dimensao), Float.parseFloat(consumoDiario));
            this.MapDevices.put(aux, bulb);
            return bulb;
        }
        else if (tonalidade.equals("Warm"))
        {
            SmartBulb bulb = new SmartBulb(aux, 1, Integer.parseInt(dimensao), Float.parseFloat(consumoDiario));
            this.MapDevices.put(aux, bulb);
            return bulb;
        }
        else if (tonalidade.equals("Cold"))
        {
            SmartBulb bulb = new SmartBulb(aux, -1, Integer.parseInt(dimensao), Float.parseFloat(consumoDiario));
            this.MapDevices.put(aux, bulb);
            return bulb;
        }
        else
        {
            System.out.println("Tonalidade inválida!\n");
            return null;
        }
    }

    // Método que faz o parse de uma smartspeaker
    public SmartSpeaker parseSmartSpeaker(String input)
    {
        int aux = this.MapDevices.size();
        String[] campos = input.split(",");
        String volume = campos[0];
        String radio = campos[1];
        String marca = campos[2];
        String consumoDiario = campos[3];

        SmartSpeaker speaker = new SmartSpeaker(aux, Integer.parseInt(volume), radio, marca, Float.parseFloat(consumoDiario));
        this.MapDevices.put(aux, speaker);
        return speaker;
    }

    // Método que faz parse de uma smartcamera
    public SmartCamera parserSmartCamera(String input)
    {
        int aux = this.MapDevices.size();
        String[] campos = input.split(",");
        String resolucao = campos[0];
        String dimensao = campos[1];
        String consumoDiario = campos[2];

        SmartCamera camera = new SmartCamera(aux, Float.parseFloat(dimensao), resolucao, Float.parseFloat(consumoDiario));
        this.MapDevices.put(aux, camera);
        return camera;
    }

    // Método que le um ficheiro
    public List<String> lerFicheiro(String nomeFich)
    {
        List<String> lines = new ArrayList<>();
        try
        {
            lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8);
        } catch (IOException exc)
        {
            System.out.println(exc.getMessage());
        }
        return lines;

    }

    // Método que faz o load de um ficheiro
    public void loadLogs()
    {
        List<String> linhas = lerFicheiro("src/logs.csv");
        Casa ultima = null;
        String divisaoAux = null;
        String[] linhaPartida;
        for (String linha : linhas)
        {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0])
            {
                case "Fornecedor":
                {
                    Fornecedor fornecedor = parseFornecedor(linhaPartida[1]);
                    this.Fornecedores.put(fornecedor.getNome(), fornecedor);
                    break;
                }
                case "Casa":
                {
                    Casa c = parseCasa(linhaPartida[1]);
                    ultima = new Casa(c);
                    this.MapCasas.put(c.getNIF(), c);
                    break;
                }
                case "Divisao":
                {
                    String divisao = parseDivisao(linhaPartida[1]);
                    divisaoAux = divisao;
                    this.MapCasas.get(ultima.getNIF()).addDivisao(divisao);
                    break;
                }
                case "SmartSpeaker":
                {
                    SmartDevice speaker = parseSmartSpeaker(linhaPartida[1]);
                    this.MapCasas.get(ultima.getNIF()).addDispositivoDivisao(divisaoAux, speaker);
                    this.MapDevices.put(speaker.getID(), speaker.clone());
                    break;
                }
                case "SmartBulb":
                {
                    SmartDevice bulb = parseSmartBulb(linhaPartida[1]);
                    this.MapCasas.get(ultima.getNIF()).addDispositivoDivisao(divisaoAux, bulb);
                    this.MapDevices.put(bulb.getID(), bulb.clone());
                    break;
                }
                case "SmartCamera":
                {
                    SmartDevice camera = parserSmartCamera(linhaPartida[1]);
                    this.MapCasas.get(ultima.getNIF()).addDispositivoDivisao(divisaoAux, camera);
                    this.MapDevices.put(camera.getID(), camera.clone());
                    break;
                }
                default:
                {
                    System.out.println("Linha Inválida\n");
                    break;
                }
            }
        }
    }
}
