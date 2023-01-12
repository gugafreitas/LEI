package src.Utilizadores;

import java.sql.SQLException;
//import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import data.UtilizadoresDAO;

public class Utilizadores {
    private Map<String, Utilizador> users;


    public Utilizadores() throws SQLException{
        this.users = UtilizadoresDAO.getInstance();
    }

    public Utilizador geUtilizador(String name) {
        return users.get(name);
    }

    public boolean checkUsername(String nomeUtilizador){
        return this.users.containsKey(nomeUtilizador);
    }

    public boolean checkPassword(String nomeUtilizador, String pass) throws Exception{
        if(checkUsername(nomeUtilizador)){
            return Objects.equals(this.users.get(nomeUtilizador).getPassword(), pass);
        }
        else{
            throw new Exception("Utilizador não existente");
        }
    }

    public boolean isPremium(String userID) throws Exception{
        if(checkUsername(userID)){
            return Objects.equals(this.users.get(userID).getTipoDeUser(), 'p');
        }
        else{
            throw new Exception("Utilizador não existente");
        }
    }

    public void regUser(String name, String pass, char tipo) throws Exception {
        if(!checkUsername(name)){
            Utilizador u = new Utilizador(name, pass, tipo,null,null,null);
            this.users.put(name, u);
        }
        else{
            throw new Exception("Utilizador já existente");
        }
    }
}

