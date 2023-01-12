public class Barrier {
    private boolean available;

    public Barrier() {
        this.available = false;
    }

    synchronized void await(){
        while(!available){
            try{
                wait();
            }catch (InterruptedException e){

            }
        }
        available = false;
        notifyAll();
    }

    synchronized void notifica(){
        while(available){
            try{
                wait();
            }catch (InterruptedException e){

            }
        }
        available = true;
        notifyAll();
    }
}
