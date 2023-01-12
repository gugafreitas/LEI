package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Trotinete {
    int id;
    private boolean ocupada;
    Coordenada coordenada;
    Lock lock;

    public Trotinete(){
        this.id = 0;
        this.ocupada = false;
        this.coordenada = new Coordenada();
        this.lock = new ReentrantLock();
    }

    public Trotinete(int id, Coordenada coordenada) {
        this.id = id;
        this.ocupada = false;
        this.coordenada = coordenada;
        this.lock = new ReentrantLock();
    }

    public Trotinete(int id, boolean ocupada, Coordenada coordenada) {
        this.id = id;
        this.ocupada = ocupada;
        this.coordenada = coordenada;
        this.lock = new ReentrantLock();
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public int getId() {
        return id;
    }

    public Coordenada getCoordenada() {
        lock.lock();
        try {
            return coordenada;
        }finally {
            lock.unlock();
        }
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public void setC(Coordenada c) {
        this.coordenada = c;
    }

    public void serialize(DataOutputStream out) throws IOException {
        out.writeInt(this.id);
        out.writeBoolean(this.ocupada);
        out.writeInt(this.coordenada.getX());
        out.writeInt(this.coordenada.getY());
    }

    public static Trotinete deserialize(DataInputStream in) throws IOException{
        int id = in.readInt();
        boolean ocp = in.readBoolean();
        int x = in.readInt();
        int y = in.readInt();
        Coordenada c = new Coordenada(x,y);
        return new Trotinete(id,ocp,c);
    }

    public double getDist(Coordenada c){
        //return Math.sqrt((c.getX()-coordenada.getX())*(c.getX()-coordenada.getX())+(c.getY()-coordenada.getY())*(c.getY()-coordenada.getY()));
        return Math.abs(c.getX()-coordenada.getX()) + Math.abs(c.getY()-coordenada.getY());
    }

    public long getTempo (float dist){
        return Math.round(60000L*dist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trotinete trotinete = (Trotinete) o;
        return id == trotinete.id && ocupada == trotinete.ocupada && Objects.equals(coordenada, trotinete.coordenada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ocupada, coordenada);
    }

    @Override
    public String toString() {
        return "Trotinete:" +
                "id=" + id +
                ", c=" + coordenada;
    }
}
