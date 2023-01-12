package src.Pilotos;

import java.util.Map;
import java.util.Objects;

//import src.Pilotos.Piloto;

import java.util.HashMap;

public class Pilotos {
    private Map<String, Piloto> pilotos;

    public Pilotos(){
        this.pilotos = new HashMap<String, Piloto>();
    }

    public Pilotos(HashMap<String, Piloto> p){
        this.pilotos = p;
    }

    public boolean isAvailable(String pName) throws Exception{
        if(pilotos.get(pName) != null){
            return pilotos.get(pName).getAvailable();
        }
        else{
            throw new Exception("Piloto Inexistente");
        }
    }

    public void changeStatus(String pName) throws Exception{
        if(pilotos.get(pName) != null){
            Piloto p = new Piloto(pilotos.get(pName));
            p.setAvailable(!p.getAvailable());
            pilotos.replace(pName, p);
        }
        else{
            throw new Exception("Piloto Inexistente");
        }
    }

    public boolean validatePiloto(String pname){//pilotos nao podem ter nomes iguais
        boolean aux = true;
        for(Map.Entry<String, Piloto> pair : this.pilotos.entrySet()){
            if(Objects.equals(pair.getValue().getNome(),pname))
                aux = false;      
        }
        return aux;
    }

    public void addPilot(float CTS, float SVA, boolean available, String nome) throws Exception{
        if(pilotos.get(nome) != null){
            throw new Exception("Piloto ja existente");
        }
        else{
            Piloto p = new Piloto(nome,available,CTS,SVA);
            pilotos.put(nome, p);
        }
    }
}
