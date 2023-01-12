import java.util.*;
import java.util.concurrent.locks.*;

//EGOISTA
class Warehouse {
    private Map<String, Product> map = new HashMap<String, Product>();
    Lock l = new ReentrantLock();

    private class Product {
        int quantity = 0;
        Condition cond = l.newCondition();
    }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String s, int quantity){
        l.lock();
        try {
            Product p = get(s);
            p.quantity += quantity;
            p.cond.signalAll();
        } finally {
            l.unlock();
        }
    }

    public void consume(String[] a) throws InterruptedException{
        l.lock();
        try{
            for(String s : a) {
                Product p = get(s);
                while(p.quantity == 0) p.cond.await();
                p.quantity -= 1;
            }
        } finally {
            l.unlock();
        }
    }
}


//COOPERATIVA
class Warehouse2 {
    private Map<String, Product> map = new HashMap<String, Product>();
    Lock l = new ReentrantLock();

    private class Product {
        int quantity = 0;
        Condition cond = l.newCondition();
    }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String s, int quantity) {
        l.lock();
        try {
            Product p = get(s);
            p.quantity += quantity;
            p.cond.signalAll();
        } finally {
            l.unlock();
        }
    }

    public void consume(String[] a) throws InterruptedException {
        l.lock();
        try {
            //se algum produto falhar volta a testar todos os produtos, para garantir que temos todos seguidos
            // (não somos egoístas)
            // no final do ciclo for temos todos os produtos disponíveis
            for (int i=0; i<a.length; ){
                Product p = get(a[i]);
                i++;
                if(p.quantity == 0){
                    p.cond.await();
                    i=0;
                }
            }
            //já tendo todos os produtos, decrementamos a quantidade de todos
            for(String s : a)
                get(s).quantity--;
        } finally {
            l.unlock();
        }
    }
}
