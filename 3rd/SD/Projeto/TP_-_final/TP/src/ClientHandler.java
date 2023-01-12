import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import DataBase.*;
import Model.*;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final BaseDados baseDados;
    private final Barrier barrier;
    private RecompensaHandler rh;
    private List<Recompensa> notificacao;
    private List<Recompensa> jaNotificado;


    public ClientHandler(Socket socket, BaseDados bd, Barrier barrier, RecompensaHandler rh){
        this.socket = socket;
        this.baseDados = bd;
        this.barrier = barrier;
        this.rh = rh;
        this.notificacao = new ArrayList<>();
        this.jaNotificado = new ArrayList<>(rh.getLr());
    }

    public void run() {
        try{
            InputStream inputStream = this.socket.getInputStream();
            OutputStream outputStream = this.socket.getOutputStream();
            DataInputStream in = new DataInputStream(inputStream);
            DataOutputStream out = new DataOutputStream(outputStream);
            while (!socket.isInputShutdown()) {
                String msg = in.readUTF();
                String[] split = msg.split(" ");
                String opt = split[0];
                switch (opt) {
                    case "login" -> {
                        String id = split[1];
                        int passwordHashLogin = Integer.parseInt(split[2]);
                        boolean validLogin = baseDados.getUtilizadoresBaseDados().autenticaUtilizador(id, passwordHashLogin);
                        out.writeBoolean(validLogin);
                        if (validLogin) {
                            Utilizador utilizador;
                            utilizador = baseDados.getUtilizadoresBaseDados().getUtilizadorByID(id);
                            if (utilizador == null) utilizador = new Utilizador();
                            utilizador.serialize(out);
                        }
                    }
                    case "register" -> {
                        String username = split[1];
                        String name = split[2];
                        Utilizador utilizadorAdicionado;
                        int x = (int) ((Math.random() * 20));
                        int y = (int) ((Math.random() * 20));
                        while (baseDados.getUtilizadoresBaseDados().utilizadorExiste(username)) {
                            out.writeBoolean(false);
                            username = in.readUTF();
                        }
                        out.writeBoolean(true);
                        String password = in.readUTF();
                        int password_received_hash = in.readInt();
                        utilizadorAdicionado = baseDados.getUtilizadoresBaseDados().adicionarUtilizador(username, name, password_received_hash, new Coordenada(x, y));
                        BaseDados.adicionarUtilizador(utilizadorAdicionado,password);
                        utilizadorAdicionado.serialize(out);
                    }
                    case "alterar" -> {
                        String user_ID = split[1];
                        int passwordHash = Integer.parseInt(split[2]);
                        Utilizador utilizadorAux = baseDados.getUtilizadoresBaseDados().getUtilizadorByID(user_ID);
                        if (utilizadorAux != null) utilizadorAux.setPasswordHash(passwordHash);
                    }
                    case "listaTrotinete" -> {
                        String user_id = split[1];
                        Utilizador utilizador = baseDados.getUtilizadoresBaseDados().getUtilizadorByID(user_id);
                        List<Trotinete>[][] m = baseDados.getTrotineteBaseDados().getMatriz();
                        for (int i = 0; i < baseDados.getTrotineteBaseDados().getN(); i++) {
                            for (int j = 0; j < baseDados.getTrotineteBaseDados().getN(); j++) {
                                for(Trotinete t : m[i][j]) {
                                    if (t.getDist(utilizador.getCoordenada()) <= 10) {
                                        if (!baseDados.getTrotineteBaseDados().isOcupada(t.getId()) && t.getId() > 0) {
                                            boolean ocupada = baseDados.getReservasBaseDados().reservaTrotineteIdExiste(t.getId());
                                            if (!ocupada) {
                                                out.writeBoolean(true);
                                                Coordenada c = baseDados.getTrotineteBaseDados().getCoordenadas(t.getId());
                                                String registerUserRequest = t.getId() + " " + c.getX() + " " + c.getY() + " " + t.getDist(utilizador.getCoordenada());
                                                out.writeUTF(registerUserRequest);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        out.writeBoolean(false);
                    }
                    case "reserva" -> {
                        int idTrotinete = Integer.parseInt(split[1]);
                        String idUtilizador = split[2];
                        boolean ocupada = baseDados.getReservasBaseDados().reservaTrotineteIdExiste(idTrotinete);
                        if (!ocupada) {
                            out.writeBoolean(true);
                            String id_reserva = baseDados.getReservasBaseDados().adicionaReserva(idUtilizador, idTrotinete);
                            out.writeUTF(id_reserva);
                            barrier.notifica();

                        } else {
                            out.writeBoolean(false);
                        }
                    }
                    case "listaAlugueres" -> {
                        String utilizId = split[1];
                        if(baseDados.getReservasBaseDados().existemReservasRegistados(utilizId)){
                            out.writeBoolean(true);
                            List<Reserva> list = baseDados.getReservasBaseDados().getReservasClient(utilizId);
                            for (Reserva r : list) {
                                out.writeBoolean(true);
                                r.serialize(out);
                            }
                            out.writeBoolean(false);
                        }else{
                            out.writeBoolean(false);
                        }
                    }
                    case "estacionar" -> {
                        String utilizadorId = split[1];
                        String codigo = split[2];
                        LocalDateTime time = LocalDateTime.now();
                        out.writeInt(baseDados.getTrotineteBaseDados().getN());
                        boolean exists = baseDados.getReservasBaseDados().getReservas().containsKey(codigo);
                        while (!exists || !baseDados.getReservasBaseDados().getReservas().get(codigo).getIdCliente().equals(utilizadorId)) {
                            out.writeBoolean(false);
                            codigo = in.readUTF();
                            exists = baseDados.getReservasBaseDados().getReservas().containsKey(codigo);
                        }
                        out.writeBoolean(true);
                        String msg2 = in.readUTF();
                        String[] split2 = msg2.split(" ");
                        int xDestino = Integer.parseInt(split2[0]);
                        int yDestino = Integer.parseInt(split2[1]);
                        Reserva reserva = baseDados.getReservasBaseDados().getReservaByID(codigo);
                        Trotinete trotinete = baseDados.getTrotineteBaseDados().getTrotinetes().get(reserva.getIdTrotinete());
                        Utilizador ut = baseDados.getUtilizadoresBaseDados().getUtilizadorByID(utilizadorId);
                        double distancia = trotinete.getDist(ut.getCoordenada());
                        double preco = distancia * 2; //2€ por unidade de distancia
                        preco += (time.getSecond() - reserva.getDate().getSecond()) * (1D / 60D); //1€ por minuto
                        baseDados.getTrotineteBaseDados().removeTrotinete(trotinete);
                        baseDados.getTrotineteBaseDados().adicionarTrotinete(trotinete.getId(), new Coordenada(xDestino, yDestino));
                        //baseDados.modificarTrotinete(trotinete.getId(),xDestino, yDestino);
                        baseDados.getUtilizadoresBaseDados().alterarCoordenada(ut.getId(),new Coordenada(xDestino, yDestino));

                        baseDados.getReservasBaseDados().removerReserva(codigo);
                        out.writeDouble(preco);
                        List<Recompensa> lr = new ArrayList<>(rh.getLr());
                        for(Recompensa r : lr){
                            if(trotinete.getId()==r.getTrotinete().getId() && (new Coordenada(xDestino,yDestino).equals(r.getDestino()))){
                                out.writeBoolean(true);
                                out.writeDouble(r.getRecompensa());
                                rh.removeRecompensa(r);
                            }
                        }
                        out.writeBoolean(false);
                        barrier.notifica();
                      }
                    case "recompensas" -> {
                        List<Recompensa> lr = rh.getLr();
                        for(Recompensa r : lr){
                            out.writeBoolean(true);
                            r.serialize(out);
                        }
                        out.writeBoolean(false);
                    }
                    case "alterarN" -> {
                        String utilizadorId = split[1];
                        String bool = split[2];
                        baseDados.getUtilizadoresBaseDados().setNotificacao(utilizadorId,Boolean.parseBoolean(bool));
                    }
                    case "alterarR" -> {
                        String utilizadorId = split[1];
                        String doubl = split[2];
                        baseDados.getUtilizadoresBaseDados().getUtilizadorByID(utilizadorId).setRaio(Double.parseDouble(doubl));
                    }
                    case "ativarN" -> {
                        String utilizadorId = split[1];
                        double d = Double.parseDouble(split[2]);
                        baseDados.getUtilizadoresBaseDados().setNotificacao(utilizadorId,true);
                        baseDados.getUtilizadoresBaseDados().getUtilizadorByID(utilizadorId).setRaio(d);
                    }
                    case "notificar" -> {
                        String utilizadorId = split[1];
                        while (in.readBoolean()){
                            if(baseDados.getUtilizadoresBaseDados().isNotificacao(utilizadorId))
                                notificacao.add(Recompensa.deserialize(in));
                        }
                    }
                    case "checkNotificacao" -> {
                        if(!notificacao.isEmpty()){
                            out.writeBoolean(true);
                            int size = notificacao.size();
                            for(int i = 0; i<size ; i++){
                                out.writeBoolean(true);
                                notificacao.get(i).serialize(out);
                            }
                            out.writeBoolean(false);
                            notificacao = new ArrayList<>();

                        }else{
                            out.writeBoolean(false);
                        }
                    }
                    case "atualizaNotificacao" -> {
                        int size = rh.getLr().size();
                        for(int i = 0; i<size ; i++){
                            out.writeBoolean(true);
                            rh.getLr().get(i).serialize(out);
                        }
                        out.writeBoolean(false);
                        jaNotificado.retainAll(rh.getLr());
                        size = jaNotificado.size();
                        for(int i = 0; i<size ; i++){
                            out.writeBoolean(true);
                            jaNotificado.get(i).serialize(out);
                        }
                        out.writeBoolean(false);

                        List<Recompensa> novaNotificacao = new ArrayList<>();
                        while(in.readBoolean()){
                            Recompensa r = Recompensa.deserialize(in);
                            novaNotificacao.add(r);
                        }
                        notificacao = new ArrayList<>(novaNotificacao);
                        jaNotificado = new ArrayList<>(rh.getLr());
                    }
                    default -> {
                    }
                }
            }
            this.socket.shutdownInput();
            this.socket.shutdownOutput();
            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("Conexao terminada, cliente IP - " + socket.getInetAddress() + " Port:" + socket.getPort() + " Local Port:" + socket.getLocalPort());
        }
        
    }
}
