package DataBase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Model.Utilizador;
import Model.Coordenada;

public class UtilizadoresDB implements Serializable{
    private Map<String,Utilizador> utilizadores;
    Lock lock = new ReentrantLock();

    public UtilizadoresDB() {
        this.utilizadores = new HashMap<>();
    }

    public Map<String, Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public Utilizador adicionarUtilizador(String username, String nome, int passwordHash, Coordenada c){
        Utilizador utilizador = new Utilizador(username, nome, passwordHash,c);
        lock.lock();
        try{
            if(!this.utilizadores.containsKey(username)) this.utilizadores.put(utilizador.getId(), utilizador);
            else utilizador = null;
        }finally{
            lock.unlock();
        }
        return utilizador;
    }

    public boolean removerUtilizador(String id){
        boolean utilizadorExiste = false;
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)){
                this.utilizadores.remove(id);
                utilizadorExiste = true;
            }
        }finally{
            lock.unlock();
        }
        return utilizadorExiste;
    }

    public boolean utilizadorExiste(String id){
        boolean utilizadorExiste = false;
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)) utilizadorExiste = true;
        }finally{
            lock.unlock();
        }
        return utilizadorExiste;
    }

    public Utilizador getUtilizadorByID(String id){
        Utilizador utilizador = null;
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)) utilizador = this.utilizadores.get(id);
        }finally{
            lock.unlock();
        }
        return utilizador;
    }

    public boolean autenticaUtilizador(String id, int passwordHash){
        boolean validPassword = false;
        lock.lock();
        try{
            if(this.utilizadorExiste(id)){
                Utilizador utilizador = this.utilizadores.get(id);
                if(utilizador.getPasswordHash() == passwordHash) validPassword = true;
            }
        }finally{
            lock.unlock();
        }
        return validPassword;
    }

    public void alterarCoordenada(String id, Coordenada c){
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)){
                this.utilizadores.get(id).setCoordenada(c);
            }
        }finally{
            lock.unlock();
        }
    }

    public boolean isNotificacao(String id){
        boolean r = false;
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)){
                r = this.utilizadores.get(id).isNotificacao();
            }
        }finally{
            lock.unlock();
        }
        return r;
    }

    public void setNotificacao(String id,Boolean b){
        lock.lock();
        try{
            if(this.utilizadores.containsKey(id)){
                this.utilizadores.get(id).setNotificacao(b);
            }
        }finally{
            lock.unlock();
        }
    }
}