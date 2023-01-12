import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//comunica cliente
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in)); //comunica servidor

            String userInput;
            String serverResponse;
            while ((userInput = systemIn.readLine()) != null) { //enquanto consrgue ler linhas do teclado(utilizador) envia servidor
                out.println(userInput);
                out.flush();

                String response = in.readLine(); //espera pela resposta do servidor
                System.out.println("The current sum is: " + response);
            }

            socket.shutdownOutput();

            serverResponse = in.readLine();
            System.out.println("The average is: " + serverResponse);
            socket.shutdownInput();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
