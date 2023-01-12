package src.Circuitos;

import java.util.Map;
//import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collection;

public class Circuito{
    private String nome;
    private int distancia;
    private int voltas;
    private Map<Integer,Elemento> elementos;
    private int mapId;
    private int nCurvas;
    private int nChicanes;
    private int nRetas;

    public Circuito(){
        this.nome = "";
        this.distancia = 0;
        this.voltas = 0;
        this.elementos = new HashMap<Integer,Elemento>();
        this.mapId = 0;
        this.nCurvas = 0;
        this.nChicanes = 0;
        this.nRetas = 0;
    }

    public Circuito(String nome,int distancia,int voltas,String elements,int mapId,int nCurvas,int nChicanes,int nRetas){
        this.nome = nome;
        this.distancia = distancia;
        this.voltas = voltas;
        this.elementos = comprimentoStringToMap(elements);
        this.mapId = mapId;
        this.nCurvas = nCurvas;
        this.nChicanes = nChicanes;
        this.nRetas = nRetas;
    }

    public Circuito(String nome,int distancia,int voltas,String elements,int mapId,int nCurvas,int nChicanes){
        this.nome = nome;
        this.distancia = distancia;
        this.voltas = voltas;
        this.elementos = comprimentoStringToMap(elements);
        this.mapId = mapId;
        this.nCurvas = nCurvas;
        this.nChicanes = nChicanes;
        this.nRetas = this.calcNumberStraight();
    }

    public Circuito(String nome,int distancia,int voltas,Map<Integer,Elemento> elements,int mapId,int nCurvas,int nChicanes,int nRetas){
        this.nome = nome;
        this.distancia = distancia;
        this.voltas = voltas;
        this.elementos = elements;
        this.mapId = mapId;
        this.nCurvas = nCurvas;
        this.nChicanes = nChicanes;
        this.nRetas = nRetas;
    }

    public Circuito(String nome,int distancia,int voltas,Map<Integer,Elemento> elements,int mapId,int nCurvas,int nChicanes){
        this.nome = nome;
        this.distancia = distancia;
        this.voltas = voltas;
        this.elementos = elements;
        this.mapId = mapId;
        this.nCurvas = nCurvas;
        this.nChicanes = nChicanes;
        this.nRetas = this.calcNumberStraight();
    }

    public Circuito(Circuito c){
        this.nome = c.getNome();
        this.distancia = c.getDistancia();
        this.voltas = c.getVoltas();
        this.elementos = c.getComprimento();
        this.mapId = c.getMapID();
        this.nCurvas = c.getNCurvas();
        this.nChicanes = c.getNChicanes();
        this.nRetas = c.getNRetas();
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return this.nome;
    }

    public void setDistancia(int distancia){
        this.distancia = distancia;
    }

    public int getDistancia(){
        return this.distancia;
    }

    public void setVoltas(int voltas){
        this.voltas = voltas;
    }

    public int getVoltas(){
        return this.voltas;
    }

    public void setComprimento(Map<Integer,Elemento> comprimento){
        this.elementos = comprimento;
    }

    public Map<Integer,Elemento> getComprimento(){
        return this.elementos;
    }

    public String getComprimentoString(){
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int size = this.elementos.size();
        for(Elemento c : this.elementos.values()){
            if(count < size-1){
                sb.append(Float.toString(c.getGdu()));
                sb.append(",");
                sb.append(Integer.toString(c.getTipo()));
                sb.append(" ");
                count++;
            }
            else{
                sb.append(Float.toString(c.getGdu()));
                sb.append(",");
                sb.append(Integer.toString(c.getTipo()));
            }
        }
        return sb.toString();
    }

    public void setMapID(int mid){
        this.mapId = mid;
    }

    public int getMapID(){
        return this.mapId;
    }

    public void setNCurvas(int i){
        this.nCurvas = i;
    }

    public int getNCurvas(){
        return this.nCurvas;
    }

    public void setNChicanes(int i){
        this.nChicanes = i;
    }

    public int getNChicanes(){
        return this.nChicanes;
    }

    public void setNRetas(int i){
        this.nRetas = i;
    }

    public int getNRetas(){
        return this.nRetas;
    }

    public Map<Integer,Elemento> comprimentoStringToMap(String elements){ // ElementoStringToMap 
        int count = 0;
        Map<Integer,Elemento> r = new HashMap<>();
        int aux = 0;
        int gdu = 0;
        int tipo = -1;
        for(String e : elements.split(" ")){
            aux = 0;
            for(String a : e.split(",")){
                if(aux == 0){
                    gdu = Integer.parseInt(a);
                    aux++;
                }
                else tipo = Integer.parseInt(a);
            }

            r.put(count, new Elemento(gdu,tipo));
            count++;
        }
        return r;
    }
  

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public ArrayList<Elemento> showListChicanes() {
        ArrayList<Elemento> nova = new ArrayList<Elemento>();
        
        for(Map.Entry<Integer, Elemento> pair : this.elementos.entrySet()){
            if(pair.getValue().getTipo()==3){
                nova.add(pair.getValue());

            }
        }
        return nova;
    }

    public ArrayList<Elemento> showListStraight() {
        ArrayList<Elemento> nova = new ArrayList<Elemento>();
        
        for(Map.Entry<Integer, Elemento> pair : this.elementos.entrySet()){
            if(pair.getValue().getTipo()==1){
                nova.add(pair.getValue());

            }
        }
        return nova;
    }

    public ArrayList<Elemento> showListCurves() {
        ArrayList<Elemento> nova = new ArrayList<Elemento>();

        for(Map.Entry<Integer, Elemento> pair : this.elementos.entrySet()){
            if(pair.getValue().getTipo()==2){
                nova.add(pair.getValue());

            }
        }
        return nova;
    }

    public float checkGDUreta(int idReta){
        return this.elementos.get(idReta).getGdu();
    }

    public float checkGDUcurva(int idCurva){
        return this.elementos.get(idCurva).getGdu();
    }

    public float checkGDUchicane(int idChicane){
        return this.elementos.get(idChicane).getGdu();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    

    public void regChicane(int gdu){
        Elemento aux = new Elemento(gdu,3);
        this.elementos.put(mapId,aux);
        this.mapId +=1;
    }

    public void regCurva(int gdu){
        Elemento aux = new Elemento(gdu,2);
        this.elementos.put(mapId,aux);
        this.mapId +=1;
    }

    public void regStraight(int gdu){
        Elemento aux = new Elemento(gdu,1);
        this.elementos.put(mapId,aux);
        this.mapId +=1;
    }

    public void putComprimento(Elemento nova){
        
        this.elementos.put(mapId,nova);
        this.mapId +=1;
    }
    

    public int calcNumberStraight(){
        int cur = this.nCurvas;
        int chi = this.nChicanes;

        int comp = this.distancia - cur*100 - chi * 100;
        return (comp/100);
    }

    public void printCircuito(){
        for(Map.Entry<Integer, Elemento> pair : this.elementos.entrySet()){
            if(pair.getValue().getTipo() == 1){
                System.out.println("Reta");
            }
            if(pair.getValue().getTipo() == 2){
                System.out.println("Curva");
            }
            if(pair.getValue().getTipo() == 3){
                System.out.println("Chicane");
            }
            
        }
    }

    
}