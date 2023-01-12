import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServerWorker implements Runnable{
    private Socket socket;
    @Override
    public void run() {

    }
}

public class EchoServer {

    public static void main(String[] args) {
            ServerSocket ss = new ServerSocket(12345);

            while (true) {
                Socket socket = ss.accept();

                Thread worker = new Thread(new ServerWorker(socket));
                worker.start();
            }

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());

                int sum=0;
                int n=0;
                String line;
                while ((line = in.readLine()) != null) {
                    //int array[i] = Integer.parseInt(line);
                    int soma=0;
                    soma += Integer.parseInt(line);
                    out.println(soma);
                    out.flush();
                    //i++;
                }

                if(n<1){}

                out.println(sum(double));

                socket.shutdownOutput();
                out.println();
                socket.shutdownInput();
                socket.close();
            }
          catch (IOException e) {
            e.printStackTrace();
        }
    }

