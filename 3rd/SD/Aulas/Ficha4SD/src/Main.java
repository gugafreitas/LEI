import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier{
    private int n;
    private int counter;
    private ReentrantLock lock = new ReentrantLock();
    private Condition waitingForThreads = lock.newCondition();

    public Barrier(int n){
        this.n = n;
        this.counter = 0;
    }

    public void await() throws InterruptedException {
        this.lock.lock(); //PORQUE É PARTILHADO
        this.counter++;

        while(this.counter<this.n){
            //MANDO ESPERAR
            this.waitingForThreads.signalAll();
        }

        //SINALIZO
        lock.unlock();
    }
}