package ex1;

import java.util.concurrent.locks.ReentrantLock;

class Bank {
    private ReentrantLock lock = new ReentrantLock();
    private static class Account {
        private int balance;
        Account(int balance) { this.balance = balance; }
        int balance() { return balance; }
        boolean deposit(int value) {
            balance += value;
            return true;
        }
        boolean withdraw(int value) {
            if (value > balance)
                return false;
            balance -= value;
            return true;
        }
    }

    // ex1.Bank slots and vector of accounts
    private int slots;
    private Account[] av;

    public Bank(int n)
    {
        slots=n;
        av=new Account[slots];
        for (int i=0; i<slots; i++) av[i]=new Account(0);
    }

    // Account balance
    public int balance(int id) {
        lock.lock();
        try {
            if (id < 0 || id >= slots)
                return 0;
            return av[id].balance();
        }
        finally {
            lock.unlock();
        }
    }

    // Deposit
    boolean deposit(int id, int value) {
        lock.lock();
        try {
            if (id < 0 || id >= slots)
                return false;
            return av[id].deposit(value);
        }
        finally {
            lock.unlock();
        }
    }

    // Withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        lock.lock();
        try {
            if (id < 0 || id >= slots)
                return false;
            return av[id].withdraw(value);
        }
        finally {
            lock.unlock();
        }
    }

    /*Transfer from one account to other
    public void transfer(int from, int to, int value){
        lock.lock();
        try{
            withdraw(from,value);
            deposit(to,value);
        }
        finally {
            lock.unlock();
        }
    }
    */


    //PROF
    public boolean transfer(int from, int to, int value) {
        if (withdraw(from, value))
            deposit(to, value);
        return true;
    }


    // Total of all accounts
    int totalBalance(){
        int total=0;

        this.lock.lock();

        for(int n=0;n<slots;n++)
            total += av[n].balance;

        this.lock.unlock();

        return total;
    }
}


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

