package src;

import java.util.ArrayList;
// import java.util.Collection;
// import java.util.HashSet;
// import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
// import java.util.Set;
// import java.util.TreeSet;
import java.sql.*;

import data.*;
import src.Carros.Carro;
import src.Circuitos.Circuito;
import src.Pilotos.Piloto;
import src.Utilizadores.Utilizadores;
import src.Campeonatos.Campeonato;


public class RMFacade {

    public Map<String, Piloto> pilotos;
    public Map<String, Carro> carros;
    public Map<String, Circuito> circuitos;
    public Map<String, Campeonato> campeonatos;
    public Utilizadores utilizadores; //Map<String,utilizadores>

    public RMFacade() throws SQLException {
        this.pilotos = PilotosDAO.getInstance();
        this.carros = CarrosDAO.getInstance();
        this.circuitos = CircuitosDAO.getInstance();
        this.campeonatos =  CampeonatosDAO.getInstance();
        this.utilizadores = new Utilizadores();
    }

    public ArrayList<Campeonato> showCampeonatos(){
        ArrayList<Campeonato> nova = new ArrayList<Campeonato>();

        for(Map.Entry<String, Campeonato> pair : this.campeonatos.entrySet()){
            nova.add(pair.getValue());       
        }
        return nova;
    }

    public ArrayList<Circuito> showCircuitos(){
        ArrayList<Circuito> nova = new ArrayList<Circuito>();

        for(Map.Entry<String, Circuito> pair : this.circuitos.entrySet()){
            nova.add(pair.getValue());       
        }
        return nova;
    }

    public void printCampeonatos(){
        for(Map.Entry<String, Campeonato> pair : this.campeonatos.entrySet()){
            System.out.print(pair.getValue().getNome());       
        }
    }

    public void printCircuitos(){
        for(Map.Entry<String, Circuito> pair : this.circuitos.entrySet()){
            System.out.print(pair.getValue().getNome());      
        }
    }

    public boolean checkNomeCampeonato(String nome){
        boolean aux = true;
        for(Map.Entry<String, Campeonato> pair : this.campeonatos.entrySet()){
            if(Objects.equals(nome,pair.getValue().getNome())) aux = false;         
        }
        return aux;
    }

    public boolean checkNameCircuito(String nome){
        boolean aux = true;
        for(Map.Entry<String, Circuito> pair : this.circuitos.entrySet()){
            if(Objects.equals(nome,pair.getValue().getNome())) aux = false;         
        }
        return aux;
    }

    public void showListCurveStraight(Circuito c){
        c.printCircuito();
    }

    public void addCampeonato(Campeonato c){
        this.campeonatos.put(c.getNome(), c);
    }

    public void addCircuito(Circuito c){
        this.circuitos.put(c.getNome(), c);
    }

    public Campeonato chooseChampionship(String cName){
        return this.campeonatos.get(cName);
    }

    public Carro chooseCar(String cName){
        return this.carros.get(cName);
    }

    public Piloto choosePilot(String cName){
        return this.pilotos.get(cName);
    }

    


}
