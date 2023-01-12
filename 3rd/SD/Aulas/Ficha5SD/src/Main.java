import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
    private Map<String, Product> map =  new HashMap<String, Product>();
    private Lock lock = new ReentrantLock();
    private class Product {
        int quantity = 0;
        Condition isEmpty = lock.newCondition();
    }

    private Product get(String item) {
        Product p = map.get(item);
        if (p != null) return p;
        p = new Product();
        map.put(item, p);
        return p;
    }

    public void supply(String item, int quantity) {
        lock.lock();
        Product p = get(item);
        p.quantity += quantity;
        item.isEmpty()

    }

    // Errado se faltar algum produto...
    public void consume(Set<String> items) {
        lock.lock();
        for (String s : items){
            get(s).quantity--;
        }
}

public class Main {
    public static void main(String[] args) {System.out.println("Hello world!");}
}