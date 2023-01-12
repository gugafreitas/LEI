import java.util.concurrent.locks.*;

import static java.lang.Math.max;

class Barrier{
    private int c = 0;
    private int n;
    Lock l = new ReentrantLock();
    Condition cond = l.newCondition();

    Barrier(int n){ this.n = n; }

    void await() throws InterruptedException {
        l.lock();
        try {
            c += 1;
            if (c<n)
                while(c<n) cond.await();
            else cond.signalAll();
        } finally {
            l.unlock();
        }
    }
}

class barrierReutilizavel{
    private int c = 0;
    private int epoch = 0;
    private int n;
    Lock l = new ReentrantLock();
    Condition cond = l.newCondition();

    barrierReutilizavel(int n) { this.n = n;}

    void await() throws InterruptedException{
        l.lock();
        try{
            int e = epoch;
            c+=1;
            if(c<n)
                while(epoch == e) cond.await();
            else{
                cond.signalAll();
                c=0;
                epoch += 1;
            }
        } finally {
            l.unlock();
        }
    }
}

class Agreement{
    private static class Instance{
        int result = Integer.MIN_VALUE;
        int c = 0;
    }
    Lock l = new ReentrantLock();
    Condition cond = l.newCondition();
    private int n;
    private Instance agmnt = new Instance();



    Agreement (int n) { this.n = n; }

    int propose(int choice) throws InterruptedException{
        l.lock();
        try{
            Instance agmnt = this.agmnt;
            agmnt.c += 1;
            agmnt.result = max(agmnt.result, choice);
            if(agmnt.c < n)
                while(this.agmnt == agmnt) cond.await();
            else{
                cond.signalAll();
                this.agmnt = new Instance();
            }
            return agmnt.result;
        } finally {
            l.unlock();
        }
    }
}