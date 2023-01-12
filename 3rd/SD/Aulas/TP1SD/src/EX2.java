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

class Deposito implements Runnable{
    private Bank bank;

    public Deposito(Bank b){
        this.bank=b;
    }

    public void run() {
        final long I=1000;
        for(long i=0;i<I;i++)
            bank.deposit(100);
    }
}

    class MainEx2 {
        public static void main(String[] args) throws InterruptedException {
            int N=10;
            Thread threads[] = new Thread[N];
            Bank b = new Bank();

            //1.Criar threads
            for (int i=0;i<N;i++){
                threads[i] = new Thread(new Deposito(b));
            }

            //2.Iniciar threads
            for (int i=0;i<N;i++){
                threads[i].start();
            }

            //3.espera por thread
            for (int i=0;i<N;i++){
                threads[i].join();
            }

            System.out.println(b.balance());
        }
    }
