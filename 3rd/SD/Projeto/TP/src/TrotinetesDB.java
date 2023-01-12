import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TrotinetesDB {
    private Map<Integer, Trotinete> trotinetes;
    private List<Trotinete>[][] matriz;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writelock = lock.writeLock();
    Lock readlock = lock.readLock();
    private int N;

    public TrotinetesDB() {
        this.N = 20;
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

    public Coordenada getCoordenadas(int id){
        Coordenada c = new Coordenada(0,0);
        readlock.lock();
        try {
            for (Map.Entry<Integer, Trotinete> set : trotinetes.entrySet()) {
                if (set.getValue().getId() == id) {
                    c = set.getValue().getCoordenada();
                }
            }
        }finally {
            readlock.unlock();
        }
        return c;
    }

    public Trotinete adicionarTrotinete(int id, Coordenada c){
        Trotinete trotinete = new Trotinete(id,c);
        writelock.lock();
        try{
            List<Trotinete> l = matriz[c.getX()][c.getY()];
            if(!l.contains(trotinete)){
                l.add(trotinete);
                matriz[c.getX()][c.getY()] = l;
            }
            this.trotinetes.put(trotinete.getId(), trotinete);
        }finally{
            writelock.unlock();
        }
        return trotinete;
    }

    public void removeTrotinete(Trotinete t){
        writelock.lock();
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
            l.remove(i);
            matriz[t.getCoordenada().getX()][t.getCoordenada().getY()] = l;
        }finally{
            writelock.unlock();
        }
    }
}
