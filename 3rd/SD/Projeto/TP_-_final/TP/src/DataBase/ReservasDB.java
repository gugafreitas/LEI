package DataBase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Model.Reserva;

public class ReservasDB implements Serializable {
    private Map<String,Reserva> reservas;
    Lock lock = new ReentrantLock();
    public ReservasDB() {
        this.reservas = new HashMap<>();
    }

    public Map<String, Reserva> getReservas() {
        return reservas;
    }

    public String adicionaReserva(String idCliente, int idTrotinete){
        String id = getIdUnico();
        Reserva reserva = new Reserva(id, idCliente, idTrotinete ,LocalDateTime.now());
        String r;
        lock.lock();
        try{
            this.reservas.put(reserva.getIdReserva(), reserva);
            r = reserva.getIdReserva();
        }finally{
            lock.unlock();
        }
        return r;
    }

    public void removerReserva(String id){
        lock.lock();
        try{
            this.reservas.remove(id);
        }finally{
            lock.unlock();
        }
    }

    public boolean reservaExiste(String id){
        boolean reservaExiste = false;
        lock.lock();
        try{
            if(this.reservas.containsKey(id)) reservaExiste = true;
        }finally{
            lock.unlock();
        }
        return reservaExiste;
    }

    public boolean reservaTrotineteIdExiste(int id){
        boolean reservaExiste = false;
        lock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdTrotinete()==id)
                    reservaExiste = true;
            }
        }finally{
            lock.unlock();
        }
        return reservaExiste;
    }

    public Reserva getReservaByID(String id){
        Reserva reserva = null;
        lock.lock();
        try{
            if(this.reservas.containsKey(id)) reserva = this.reservas.get(id);
        }finally{
            lock.unlock();
        }
        
        return reserva;
    }

    public List<Reserva> getReservasClient(String id){
        List<Reserva> l = new ArrayList<>();
        lock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdCliente().equals(id))
                    l.add(set.getValue());
            }
        }finally{
            lock.unlock();
        }
        return l;
    }

    public boolean existemReservasRegistados(String idCliente){
        lock.lock();
        try{
            for (Map.Entry<String, Reserva> set : reservas.entrySet()) {
                if(set.getValue().getIdCliente().equals(idCliente))
                    return true;
            }
        }finally{
            lock.unlock();
        }
        return false;
    }

    private String getIdUnico(){
        lock.lock();
        try {
            String id;
            do {
                id = UUID.randomUUID().toString().substring(0, 8);
            } while (this.reservas.containsKey(id));
            return id;
        }finally {
            lock.unlock();
        }
	}
}
