import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import DataBase.*;

public class Server {
    public static void main(String[] args) throws IOException {
        //Imports imports = new Imports();
        //imports.loadBaseDados();
        //BaseDados baseDados = imports.getBaseDados();
        BaseDados baseDados = new BaseDados();
        baseDados.loadBaseDados();
        Barrier barrier = new Barrier();
        RecompensaHandler rh = new RecompensaHandler(barrier,baseDados);
        Thread t1 = new Thread(rh);
        t1.start();

        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("-----Servidor iniciado-----");
        while(true){
            Socket socket = serverSocket.accept();
            System.out.println("Novo cliente - IP:" + socket.getInetAddress() + " Port:" + socket.getPort() + " Local Port:" + socket.getLocalPort());
            Thread clientThread = new Thread(new ClientHandler(socket,baseDados,barrier,rh));
            clientThread.start();
        }
    }
}
