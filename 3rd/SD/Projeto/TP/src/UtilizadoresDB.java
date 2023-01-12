import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UtilizadoresDB implements Serializable{
    private Map<String,Utilizador> utilizadores;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writelock = lock.writeLock();
    Lock readlock = lock.readLock();

    public UtilizadoresDB() {
        this.utilizadores = new HashMap<>();
    }

    public Map<String, Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public Utilizador adicionarUtilizador(String username, String nome, int passwordHash, Coordenada c){
        Utilizador utilizador = new Utilizador(username, nome, passwordHash,c);
        writelock.lock();
        try{
            if(!this.utilizadores.containsKey(username)) this.utilizadores.put(utilizador.getId(), utilizador);
            else utilizador = null;
        }finally{
            writelock.unlock();
        }
        return utilizador;
    }

    public boolean removerUtilizador(String id){
        boolean utilizadorExiste = false;
        writelock.lock();
        try{
            if(this.utilizadores.containsKey(id)){
                this.utilizadores.remove(id);
                utilizadorExiste = true;
            }
        }finally{
            writelock.unlock();
        }
        return utilizadorExiste;
    }

    public boolean utilizadorExiste(String id){
        boolean utilizadorExiste = false;
        readlock.lock();
        try{
            if(this.utilizadores.containsKey(id)) utilizadorExiste = true;
        }finally{
            readlock.unlock();
        }
        return utilizadorExiste;
    }

    public Utilizador getUtilizadorByID(String id){
        Utilizador utilizador = null;
        readlock.lock();
        try{
            if(this.utilizadores.containsKey(id)) utilizador = this.utilizadores.get(id);
        }finally{
            readlock.unlock();
        }
        return utilizador;
    }

    public boolean autenticaUtilizador(String id, int passwordHash){
        boolean validPassword = false;
        readlock.lock();
        try{
            if(this.utilizadorExiste(id)){
                Utilizador utilizador = this.utilizadores.get(id);
                if(utilizador.getPasswordHash() == passwordHash) validPassword = true;
            }
        }finally{
            readlock.unlock();
        }
        return validPassword;
    }
}