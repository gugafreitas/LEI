package DataBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import Model.Trotinete;
import Model.Coordenada;

public class TrotinetesDB {
    private Map<Integer, Trotinete> trotinetes;
    private List<Trotinete>[][] matriz;
    Lock lock = new ReentrantLock();
    private static final int N = 20;
    private static final int D = 10;

    public TrotinetesDB() {
        this.trotinetes = new HashMap<>();
        this.matriz = new ArrayList[N][N];
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                matriz[i][j]=new ArrayList<>();
            }
        }
    }
 // TODO ver o gets
    public Map<Integer, Trotinete> getTrotinetes() {
        return trotinetes;
    }

    public List<Trotinete>[][] getMatriz() {
        return matriz;
    }

    public int getN() {
        return N;
    }

    public int getD() {
        return D;
    }

    public Coordenada getCoordenadas(int id){
        Coordenada c = new Coordenada(0,0);
        lock.lock();
        try {
            for (Map.Entry<Integer, Trotinete> set : trotinetes.entrySet()) {
                if (set.getValue().getId() == id) {
                    c = set.getValue().getCoordenada();
                }
            }
        }finally {
            lock.unlock();
        }
        return c;
    }

    public Trotinete adicionarTrotinete(int id, Coordenada c){
        Trotinete trotinete = new Trotinete(id,c);
        lock.lock();
        try{
            List<Trotinete> l = matriz[c.getX()][c.getY()];
            if(!l.contains(trotinete)){
                l.add(trotinete);
                matriz[c.getX()][c.getY()] = l;
            }
            this.trotinetes.put(trotinete.getId(), trotinete);
        }finally{
            lock.unlock();
        }
        return trotinete;
    }

    public void removeTrotinete(Trotinete t){
        lock.lock();
        try{
            List<Trotinete> l = matriz[t.getCoordenada().getX()][t.getCoordenada().getY()];
            int i = 0;
            boolean remove = false;
            for(Trotinete trotinete : l) {
                if (trotinete.getId() == t.getId()) {
                    remove = true;
                    break;
                }
                i++;
            }
            if(remove)
                l.remove(i);
            matriz[t.getCoordenada().getX()][t.getCoordenada().getY()] = l;
        }finally{
            lock.unlock();
        }
    }

    public boolean isOcupada(int id){
        boolean r = false;
        lock.lock();
        try {
            for (Map.Entry<Integer, Trotinete> set : trotinetes.entrySet()) {
                if (set.getValue().getId() == id) {
                    r = set.getValue().isOcupada();
                }
            }
        }finally {
            lock.unlock();
        }
        return r;
    }

    public Trotinete getTrotinete(int id){
        lock.lock();
        try {
            if(trotinetes.containsKey(id))
                return trotinetes.get(id);
        }finally {
            lock.unlock();
        }
        return null;
    }

}
