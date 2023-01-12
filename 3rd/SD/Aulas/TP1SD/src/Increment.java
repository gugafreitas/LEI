public class Increment implements Runnable{
    @Override
    public void run() {
        final long I=100;

        for(long i=0;i<I;i++) System.out.println(i);
    }
}

    class MainEx1 {
        public static void main(String[] args) throws InterruptedException {
            int N=10;
            Thread threads[] = new Thread[N];

            //1. criar threads
            for (int i=0;i<N;i++){
                threads[i]=new Thread(new Increment());
            }

            //2. iniciar threads
            for (int i=0;i<N;i++){
                threads[i].start();
            }

            //3.espera por thread
            for (int i=0;i<N;i++){
                threads[i].join();
            }

            //4.fim
            System.out.println("fim");
            }
    }
