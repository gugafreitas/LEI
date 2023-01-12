package src.Campeonatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import src.RMFacade;
import src.Circuitos.Circuito;

import data.CircuitosDAO;

public class Campeonato{
    private String nome;
    private Map<String,Circuito> circuitos;

    public Campeonato(){
        this.nome = "";
        this.circuitos = new HashMap<String,Circuito>();
    }

    public Campeonato(Campeonato c){
        this.nome = c.nome;
        this.circuitos = c.circuitos;
    }

    public Campeonato(String nome,HashMap<String,Circuito> circuitos){
        this.nome = nome;
        this.circuitos = circuitos;
    }
    

    public Campeonato(String nome,String circuitosIDs,RMFacade rm){
        this.nome = nome;
        this.circuitos = circuitosIDtoMap(circuitosIDs,rm);
    }

    public Campeonato(String nome,String circuitosIDs){
        this.nome = nome;
        //this.circuitos = circuitosIDtoMap(circuitosIDs);
    }

    public String circuitosIDtoString(){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int size = this.circuitos.size();
        for(String s : this.circuitos.keySet()){
            if(count < size-1){
                sb.append(s);
                sb.append(" ");
            }
            else sb.append(s);
        }
        return sb.toString();
    }

    public Map<String,Circuito> circuitosIDtoMap(String circuitosIds,RMFacade rm){
        Map<String,Circuito> r = new HashMap<>();
        for(String id : circuitosIds.split(" ")){
            Circuito c = rm.circuitos.get(id);
            r.put(id, c);
        }

        return r;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Map<String,Circuito> getCircuitos(){
        return this.circuitos;
    }

    public void setCircuitos(HashMap<String,Circuito> circuitos){
        this.circuitos = circuitos;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void regNomeCampeonato(String nome){
        this.setNome(nome);
    }

    //public boolean checkNomeCampeonato(String nome){} // função é fora do campeonato

    public void placeCircuito(Circuito c){
        String n = c.getNome();
        this.circuitos.put(n, c);
    }

    public ArrayList<Circuito> showCircuitos(){
        ArrayList<Circuito> nova = new ArrayList<Circuito>();

        for(Map.Entry<String, Circuito> pair : this.circuitos.entrySet()){
            nova.add(pair.getValue());       
        }
        return nova;
    }
}