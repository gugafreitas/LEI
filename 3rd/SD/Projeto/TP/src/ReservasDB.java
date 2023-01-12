import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReservasDB implements Serializable {
    private Map<String,Reserva> reservas;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writelock = lock.writeLock();
    Lock readlock = lock.readLock();
    public ReservasDB() {
        this.reservas = new HashMap<>();
    }

    public Map<String, Reserva> getReservas() {
        return reservas;
    }

    public Reserva adicionaReserva(String idCliente, int idTrotinete){
        String id = getIdUnico();
        Reserva reserva = new Reserva(id, idCliente, idTrotinete ,LocalDateTime.now());
        writelock.lock();
        try{
            this.reservas.put(reserva.getIdReserva(), reserva);
        }finally{
            writelock.unlock();
        }
        return reserva;
    }

    public void removerReserva(String id){
        writelock.lock();
        try{
            this.reservas.remove(id);
        }finally{
            writelock.unlock();
        }
    }

    public boolean reservaExiste(String id){
        boolean reservaExiste = false;
        
        readlock.lock();
        try{
            if(this.reservas.containsKey(id)) reservaExiste = true;
        }finally{
            readlock.unlock();
        }
        return reservaExiste;
    }

    public boolean reservaTrotineteIdExiste(int id){
        boolean reservaExiste = false;
        readlock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdTrotinete()==id)
                    reservaExiste = true;
            }
        }finally{
            readlock.unlock();
        }
        return reservaExiste;
    }

    public Reserva getReservaByID(String id){
        Reserva reserva = null;
        readlock.lock();
        try{
            if(this.reservas.containsKey(id)) reserva = this.reservas.get(id);
        }finally{
            readlock.unlock();
        }
        
        return reserva;
    }

    public List<Reserva> getReservasClient(String id){
        List<Reserva> l = new ArrayList<>();
        readlock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdCliente().equals(id))
                    l.add(set.getValue());
            }
        }finally{
            readlock.unlock();
        }
        return l;
    }

    public boolean existemReservasRegistados(String idCliente){
        readlock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdCliente().equals(idCliente))
                    return true;
            }
        }finally{
            readlock.unlock();
        }
        return false;
    }

    private String getIdUnico(){
        readlock.lock();
        try {
            String id;
            do {
                id = UUID.randomUUID().toString().substring(0, 8);
            } while (this.reservas.containsKey(id));
            return id;
        }finally {
            readlock.unlock();
        }
	}

}
