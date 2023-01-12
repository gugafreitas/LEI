import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barrier {
    private boolean available;
    Lock lock;
    Condition cond;

    public Barrier() {
        this.available = false;
        this.lock = new ReentrantLock();
        this.cond = lock.newCondition();
    }

    public void await() throws InterruptedException{
        lock.lock();
        try {
            while (!available)
                cond.await();
            available = false;
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void notifica() throws InterruptedException{
        lock.lock();
        try {
            while (available)
                cond.await();
            available = true;
            cond.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
