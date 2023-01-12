import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import DataBase.*;
import Model.*;

public class RecompensaHandler implements Runnable{
    private Barrier barrier;
    private BaseDados baseDados;
    private int raio;
    private List<Recompensa> lr;
    Lock lock = new ReentrantLock();

    public RecompensaHandler(Barrier barrier, BaseDados bd) {
        this.barrier = barrier;
        this.baseDados = bd;
        this.raio = 3;
        this.lr = new ArrayList<>();
    }

    public List<Recompensa> getLr() {
        lock.lock();
        try {
            return lr;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (true){
            atualizar();

            List<Trotinete>[][] m = baseDados.getTrotineteBaseDados().getMatriz();
            List<Trotinete> lt = new ArrayList<>(); //list de trotinetes a mais em cada coordenada (>2)
            List<Coordenada> lc = new ArrayList<>(); //lista de coordenadas sem trotinetes num raio fixo
            for (int i = 0; i < baseDados.getTrotineteBaseDados().getN(); i++) {
                for (int j = 0; j < baseDados.getTrotineteBaseDados().getN(); j++) {
                    boolean d = densidade(new Coordenada(i,j));
                    if(!d){
                        boolean d2 = densidade(lc,new Coordenada(i,j));
                        if(!d2)
                            lc.add(new Coordenada(i,j));
                    }
                    int size = m[i][j].size();
                    if(size>=2){
                        for(int k = 1; k<size ; k++)
                            lt.add(m[i][j].get(k));
                    }
                }
            }
            criarRecompensas(lt,lc);
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void atualizar(){
        lock.lock();
        try {
            Set<Recompensa> listOcupadas = new HashSet<>();
            Set<Recompensa> listAlterados = new HashSet<>();
            for (Recompensa r : lr) {
                for (Trotinete t : baseDados.getTrotineteBaseDados().getTrotinetes().values()) {
                    if (t.getId() == r.getTrotinete().getId() && t.isOcupada())
                        listOcupadas.add(r);
                }
                Trotinete t = r.getTrotinete();
                Coordenada c = baseDados.getTrotineteBaseDados().getCoordenadas(t.getId());
                if (!t.getCoordenada().equals(c))
                    listAlterados.add(r);
            }

            for (Recompensa r : listOcupadas) {
                lr.remove(r);
            }

            for (Recompensa r : listAlterados) {
                lr.remove(r);
            }
        } finally {
            lock.unlock();
        }
    }

    public void criarRecompensas(List<Trotinete> lt,List<Coordenada> lc){
        int i = 0;
        int size = lc.size();
        for(Trotinete t : lt){
            if(!contemTrotinete(t)) {
                if (i < size) {
                    while(i<size && contemDestino(lc.get(i))){
                        i++;
                    }
                    if (i<size && !contemDestino(lc.get(i))) {
                        Recompensa r = new Recompensa(t, lc.get(i));
                        this.lr.add(r);
                    }
                }else {
                    break;
                }
                i++;
            }
        }
    }

    public boolean contemTrotinete(Trotinete trotinete){
        for(Recompensa r: lr){
            if(r.getTrotinete().getId()==trotinete.getId())
                return true;
        }
        return false;
    }

    public boolean contemDestino(Coordenada c){
        for(Recompensa r: lr){
            Coordenada dest = r.getDestino();
            if(dest.getX()==c.getX() && dest.getY()==c.getY())
                return true;
        }
        return false;
    }

    public void removeRecompensa(Recompensa r){
        lock.lock();
        try {
            for (int i = 0; i < lr.size(); i++) {
                if (r.equals(lr.get(i))) {
                    lr.remove(i);
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public boolean densidade(Coordenada c){
        List<Trotinete>[][] m = baseDados.getTrotineteBaseDados().getMatriz();
        boolean d = false;
        for (int i = 0; i < baseDados.getTrotineteBaseDados().getN(); i++) {
            for (int j = 0; j < baseDados.getTrotineteBaseDados().getN(); j++) {
                for(Trotinete t : m[i][j]) {
                    if (t.getDist(c) <= raio) {
                        d = true;
                        break;
                    }
                }
            }
        }
        return d;
    }

    public boolean densidade(List<Coordenada> lc,Coordenada c){
        List<Trotinete>[][] m = baseDados.getTrotineteBaseDados().getMatriz();
        boolean d = false;
        for(Coordenada cd : lc) {
            if ((Math.abs(c.getX()-cd.getX()) + Math.abs(c.getY()-cd.getY())) <= raio) {
                d = true;
                break;
            }
        }
        return d;
    }
}
