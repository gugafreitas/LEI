import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

class ServerHandler implements Runnable{
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Reuniao reuniao;

    ServerHandler(Socket socket, Reuniao reuniao) throws IOException{
        this.socket = socket;
        this.reuniao = reuniao;
        this.out = new PrintWriter(socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run(){
        try{
            Integer myListNumber = Integer.parseInt(in.readLine());
                if (myListNumber <= 0 || 6<myListNumber) {
                    out.println("LISTA INVALIDA");
                    in.close();
                    out.close();
                    socket.close();
                    return;
                }
                reuniao.participa(myListNumber);
                out.write("ENTRE");
                out.flush();

                String msg = in.readLine();

                while (msg!="ABANDONEI"){
                    if (msg == "PARTICIPANTES"){
                        out.write(reuniao.naSala());
                        out.flush();
                    }
                    if(msg == "ESPERA"){
                        out.write(reuniao.aEspera());
                        out.flush();
                    }
                }
                reuniao.abandona(myListNumber);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

class ex2 {
    public static void main(String[] args) throws IOException{
        boolean[] listas = new boolean[6];
        Set<Integer> listasPresentes = new HashSet<>();
        Set<Integer> listasEsperando = new HashSet<>();

        Reuniao r = new Sala(listasPresentes,listas,listasEsperando);
        ServerSocket ss = new ServerSocket(12345);
        while (true){
            Socket s = ss.accept();
            Thread t = new Thread(new ServerHandler(s,r));
            t.start();
        }
    }
}


