package DataBase;
import Model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseDados {
    private UtilizadoresDB utilizadoresBaseDados;
    private TrotinetesDB trotineteBaseDados;
    private ReservasDB reservasBaseDados;

    public BaseDados(){
        this.utilizadoresBaseDados = new UtilizadoresDB();
        this.trotineteBaseDados = new TrotinetesDB();
        this.reservasBaseDados = new ReservasDB();
    }

    public UtilizadoresDB getUtilizadoresBaseDados() {
        return this.utilizadoresBaseDados;
    }

    public TrotinetesDB getTrotineteBaseDados() {
        return trotineteBaseDados;
    }

    public ReservasDB getReservasBaseDados() {
        return reservasBaseDados;
    }

    public void setUtilizadoresDataBase(UtilizadoresDB utilizadoresDataBase) {
        this.utilizadoresBaseDados = utilizadoresDataBase;
    }

    public void setReservasBaseDados(ReservasDB reservasBaseDados) {
        this.reservasBaseDados = reservasBaseDados;
    }

    public void setTrotineteBaseDados(TrotinetesDB trotineteBaseDados) {
        this.trotineteBaseDados = trotineteBaseDados;
    }

    public void loadBaseDados() throws IOException{
        loadUtilizadores();
        loadTrotinetes();
    }

    public void loadUtilizadores() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("files/utilizadores.txt"));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 4);
            utilizadoresBaseDados.adicionarUtilizador(parts[0],parts[1],parts[2].hashCode(),new Coordenada((int) ((Math.random() * 20)),(int) ((Math.random() * 20))));
        }
        reader.close();
    }

    public void loadTrotinetes() throws IOException {
        for(int i = 1; i<16 ; i++){
            int x = (int) ((Math.random() * 20));
            int y = (int) ((Math.random() * 20));
            trotineteBaseDados.adicionarTrotinete(i, new Coordenada(x, y));
        }
    }

    public void loadTrotinetes1() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("files/trotinetes.txt"));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 4);
            trotineteBaseDados.adicionarTrotinete(Integer.parseInt(parts[0]),new Coordenada(Integer.parseInt(parts[1]),Integer.parseInt(parts[2])));
        }
        reader.close();
    }

    public static synchronized void modificarTrotinete(int id, int x, int y){
        try {
            List<String> l = new ArrayList<>();
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("files/trotinetes.txt"));
            while ((line = reader.readLine()) != null) {
                l.add(line);
            }
            reader.close();
            FileWriter fw = new FileWriter("files/trotinetes.txt");
            for (String s : l) {
                String[] split = s.split(" ");
                if (Integer.parseInt(split[0]) == id) {
                    fw.append(split[0]).append(" ").append(String.valueOf(x)).append(" ").append(String.valueOf(y)).append("\n");
                } else {
                    fw.append(s).append("\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void adicionarUtilizador(Utilizador utilizador, String password) {
        try {
                FileWriter fw = new FileWriter("files/utilizadores.txt", true);
                fw.append(utilizador.getId()).append(" ").append(utilizador.getName()).append(" ").append(password).append("\n");fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
