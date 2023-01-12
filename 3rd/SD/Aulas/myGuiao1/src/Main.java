class Increment implements Runnable {
    public void run() {
        final long I=100;

        for (long i = 0; i < I; i++)
            System.out.println(i);
    }
}

class Bank {

    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
    }

    // Our single account, for now
    private Account savings = new Account(0);

    // Account balance
    public int balance() {
        return savings.balance();
    }

    // Deposit
    boolean deposit(int value) {
        return savings.deposit(value);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int N=10;
        Thread threads[] = new Thread[N];

        for(int i=0;i<N;i++){
            threads[i] = new Thread();
        }

        for(int i=0;i<N;i++){
            threads[i].start();
        }

        for(int i=0;i<N;i++){

        }

        for(int i=0;i<N;i++){
            threads[i].join();
        }

        System.out.println("fim");
    }
}