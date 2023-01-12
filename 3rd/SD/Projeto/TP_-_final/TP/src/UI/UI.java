package UI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import Model.*;

public class UI implements Serializable{

    private transient Scanner scan;
    private DataInputStream in;
    private DataOutputStream out; 

    public UI(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void run() {
        scan = new Scanner(System.in);
        this.menu();
    }

    private void menu() {
        Menu menu = new Menu(new String[]{
                "Registar Utilizador ",
                "Autenticar Utilizador "
        });
        menu.setTitulo("Gestão de frota");
        menu.setHandler(1, this::registarUtilizador);
        menu.setHandler(2, this::autenticaUtilizador);
        menu.run();
    }

    public void registarUtilizador(){
        Menu.show4("Insira o seu nome:\n>");
        String name = scan.nextLine();
        Menu.show4("Insira um nome de utilizador:\n>");
        String username = scan.nextLine();
        String registerUserRequest = "register " + username + " " + name+ " ";
        try {
            out.writeUTF(registerUserRequest);
            while (!in.readBoolean()) {
                Menu.showErrorMessage("Nome de utilizador já existente");
                Menu.show4("Insira um nome de utilizador:\n>");
                username = scan.nextLine();
                out.writeUTF(username);
            }
            String password;
            String passwordCheck;
            do {
                Menu.show4("Insira a sua password:\n>");
                password = scan.nextLine();
                Menu.show4("Confirme a sua password:\n>");
                passwordCheck = scan.nextLine();
                if(!password.equals(passwordCheck)){
                    Menu.showErrorMessage("As duas password não coincidem");
                }
            } while (!password.equals(passwordCheck));
            out.writeUTF(password);
            out.writeInt(password.hashCode());
            Utilizador utilizadorAdicionado = Utilizador.deserialize(in);
            Menu.show4("Adicionado com sucesso: " + utilizadorAdicionado.getId());
        } catch (IOException e) {
            Menu.showErrorMessage("Não foi possível efetuar ligação com o servidor");
        }
    }

    public void autenticaUtilizador(){
        Menu.show4("-- Autenticação Utilizador --\nInsira o seu nome de Utilizador:\n>");
        String id = scan.nextLine();
        Menu.show4("Insira a sua password:\n>");
        String password = scan.nextLine();
        String loginRequest = "login " + id + " " + password.hashCode() + " ";
        try {
            out.writeUTF(loginRequest);
            boolean loginAccepted = in.readBoolean();
            if(loginAccepted){
                Utilizador utilizador = Utilizador.deserialize(in);
                menuUtilizador(utilizador);
            } else Menu.showErrorMessage("Password ou utilizador inválido");
        } catch (IOException e) {
            Menu.showErrorMessage("Não foi possível efetuar ligação com o servidor");
        }
    }

    public void menuUtilizador(Utilizador utilizador) throws IOException {
        Menu menu = new Menu(new String[]{
            "Alterar password",
            "Alugar trotinete",
            "Estacionar trotinete",
            "Lista de alugeres",
            "Lista de recompensas",
            "Notificações",
        });
        menu.setTitulo( utilizador.getName() + " - Área autenticada");
        menu.setHandler(1, () -> alterarPassword(utilizador));
        menu.setHandler(2, () -> alugarTrotinete(utilizador));
        menu.setHandler(3, () -> estacionarTrotinete(utilizador));
        menu.setHandler(4, () -> listarAlugueres(utilizador));
        menu.setHandler(5, () -> listarRecompensas(utilizador));
        menu.setHandler(6, () -> notificacoes(utilizador));
        menu.run();
    }

    public void alterarPassword(Utilizador utilizador){
        String password,passwordCheck = "";
        do {
            Menu.show4("Insira a nova password:\n>");
            password = scan.nextLine();
            Menu.show4("Confirme a password:\n>");
            passwordCheck = scan.nextLine();
            if(!password.equals(passwordCheck)){
                Menu.showErrorMessage("As duas password não coincidem");
            }
        } while (!password.equals(passwordCheck));
        String changePasswordRequest;
        changePasswordRequest = "alterar " + utilizador.getId() + " " + password.hashCode();
        try {
            out.writeUTF(changePasswordRequest);
            Menu.show4("Password alterada com sucesso");
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        } catch (IOException e) {
            Menu.showErrorMessage("Não foi possível efetuar ligação com o servidor");
        }
    }

    public void alugarTrotinete(Utilizador utilizador){
        String registerUserRequest = "listaTrotinete "+utilizador.getId();
        try {
            out.writeUTF(registerUserRequest);
            List<String> l = new ArrayList<>();
            List<Integer> listId = new ArrayList<>();
            int i = 0;
            while (in.readBoolean()) {
                String msg = in.readUTF();
                StringTokenizer token = new StringTokenizer(msg, " ");
                String id = token.nextToken();
                String x = token.nextToken();
                String y = token.nextToken();
                String dist = token.nextToken();
                listId.add(Integer.parseInt(id));
                l.add("Trotinete id: " + id + " Coordenadas: (" + x + "," + y + ") Distancia: " + dist);
            }
            if (!l.isEmpty()) {
                Menu.show3(utilizador.getName() + " - Trotinetes disponiveis",l);
                int opt = scan.nextInt();
                scan.nextLine(); //press enter
                if(opt>0 && opt<=l.size()){
                    trotineteEscolhida(utilizador, listId.get(opt - 1));
                }
            } else {
                Menu.show4("Não existem trotinetes disponiveis na area\nPrima enter para continuar\n");
                scan.nextLine();
                List<Recompensa> list = new ArrayList<>();
                while(in.readBoolean()){
                    Recompensa r = Recompensa.deserialize(in);
                    list.add(r);
                }
                atualizaNotificacao(utilizador);
                notificar(utilizador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void trotineteEscolhida(Utilizador utilizador, int id){
        try {
            String msg = "reserva "+id+" "+utilizador.getId();
            out.writeUTF(msg);
            boolean sucesso = in.readBoolean();
            if(sucesso) {
                String idReserva = in.readUTF();
                Menu.show4("Reserva feita com sucesso, código de reserva: "+idReserva+"\nPrima enter para continuar\n");
                scan.nextLine();
            }else{
                Menu.show4("Trotinete escolhida está reservada\nPressione enter para continuar e escolha outra trotinete\n");
                scan.nextLine();
            }
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void estacionarTrotinete(Utilizador utilizador){
        try {
            Menu.show4("Indique o código de reserva da trotinete que pretende estacionar\nOu prima 0 para voltar atrás\n>");
            String codigo = scan.nextLine();
            if(!codigo.equals("0")) {
                String msg = "estacionar " + utilizador.getId() + " " + codigo;
                out.writeUTF(msg);
                int n = in.readInt();
                boolean valido = in.readBoolean();
                while (!valido) {
                    Menu.show4("Código inválido\n>");
                    codigo = scan.nextLine();
                    out.writeUTF(codigo);
                    valido = in.readBoolean();
                }
                boolean validXY = false;
                int x = 0;
                int y = 0;
                Menu.show4("Indique o local onde pretende estacionar dando as coordenadas (x,y) entre (0,0) e (" + (n - 1) + "," + (n - 1) + ")\n");
                while (!validXY) {
                    Menu.show4("Indique a coordenada x\n>");
                    x = Integer.parseInt(scan.nextLine());
                    if (x >= 0 && x < n) {
                        validXY = true;
                    } else {
                        Menu.show4("Coordenada inválida\n>");
                    }
                }
                validXY = false;
                Menu.show4("Indique a coordenada y\n>");
                while (!validXY) {
                    y = Integer.parseInt(scan.nextLine());
                    if (y >= 0 && y < n) {
                        validXY = true;
                    } else {
                        Menu.show4("Coordenada inválida\n>");
                    }
                }
                msg = x + " " + y;
                out.writeUTF(msg);
                double preco = in.readDouble();
                if (in.readBoolean()) {
                    double recompensa = in.readDouble();
                    double desconto = preco - recompensa;
                    Menu.show4("Preço da reserva é " + String.format("%.2f", preco) +
                            "\nMas como recebeu uma recompensa de " + String.format("%.2f", recompensa) +
                            "\nO valor a pagar é " + String.format("%.2f", desconto) + "€\n\nPrima enter para continuar\n");
                    scan.nextLine();
                } else {
                    Menu.show4("Preço da reserva é " + String.format("%.2f", preco) + "€\n\nPrima enter para continuar\n");
                    scan.nextLine();
                }
            }
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarAlugueres(Utilizador utilizador){
        try {
            String msg = "listaAlugueres "+utilizador.getId();
            out.writeUTF(msg);
            if(in.readBoolean()){
                List<String> lr = new ArrayList<>();
                while(in.readBoolean()){
                    Reserva r = Reserva.deserialize(in);
                    lr.add(r.toString());
                }
                String titulo = "Lista de alugueres ativos";
                Menu.show2(titulo,lr);
                scan.nextLine();
            }else{
                Menu.show4("Não tem nenhum aluguer ativo\nPrima enter para continuar\n");
                scan.nextLine();
            }
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarRecompensas(Utilizador utilizador){
        try {
            String msg = "recompensas";
            out.writeUTF(msg);
            List<String> lr = new ArrayList<>();
            while(in.readBoolean()){
                Recompensa r = Recompensa.deserialize(in);
                Trotinete t = r.getTrotinete();
                Coordenada c = r.getDestino();
                double p = r.getRecompensa();
                lr.add("Recompensa:   Trotinete id="+t.getId()+"   destino->("+c.getX()+","+c.getY()+")   Recompensa="+p);
            }
            if(!lr.isEmpty()){
                Menu.show2("Lista de recompensas",lr);
                scan.nextLine();
            }else{
                Menu.show4("Não há recompensas disponíveis\nPrima enter para continuar\n");
                scan.nextLine();
            }
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notificacoes(Utilizador utilizador) {
        try {
            if (utilizador.isNotificacao()) {
                List<String> l = new ArrayList<>();
                l.add("Desativar notificações");
                l.add("Alterar raio");
                Menu.show3("Notificações", l);
                int opt = scan.nextInt();
                if (opt == 1) {
                    String msg = "alterarN " + utilizador.getId() + " false";
                    out.writeUTF(msg);
                    utilizador.setNotificacao(false);
                } else if (opt == 2) {
                    Menu.show4("Indique um novo raio\n>");
                    double d = scan.nextDouble();
                    String msg = "alterarR " + utilizador.getId() + " " + d;
                    out.writeUTF(msg);
                    utilizador.setRaio(d);
                }
            } else {
                List<String> l = new ArrayList<>();
                l.add("Ativar notificações");
                Menu.show3("titulo", l);
                int opt = scan.nextInt();
                if (opt == 1) {
                    Menu.show4("Indique o a distancia maxima para recompensas\n>");
                    double d = scan.nextDouble();
                    String msg = "ativarN " + utilizador.getId() + " " + d;
                    out.writeUTF(msg);
                    utilizador.setNotificacao(true);
                    utilizador.setRaio(d);
                }
            }
            atualizaNotificacao(utilizador);
            notificar(utilizador);
        }catch (IOException e){

        }
    }

    public void notificar(Utilizador utilizador) throws IOException {
        if(utilizador.isNotificacao()) {
            out.writeUTF("checkNotificacao ");
            if (in.readBoolean()) {
                List<String> notificacoes = new ArrayList<>();
                while (in.readBoolean()) {
                    notificacoes.add(Recompensa.deserialize(in).toString());
                }
                Menu.show2("Novas recompensas disponiveis para um raio=" + utilizador.getRaio(), notificacoes);
                scan.nextLine();
            }
        }
    }

    public void atualizaNotificacao(Utilizador utilizador) throws IOException {
        out.writeUTF("atualizaNotificacao");
        List<Recompensa> listaRecompensas = new ArrayList<>();
        List<Recompensa> notificacoes = new ArrayList<>();
        List<Recompensa> jaNotificado = new ArrayList<>();
        double raio = utilizador.getRaio();
        Coordenada c = utilizador.getCoordenada();
        while(in.readBoolean()){
            Recompensa r = Recompensa.deserialize(in);
            listaRecompensas.add(r);
        }
        while(in.readBoolean()){
            Recompensa r = Recompensa.deserialize(in);
            jaNotificado.add(r);
        }
        for(Recompensa r : listaRecompensas ) {
            if (r.getTrotinete().getDist(c) <= raio && !notificadoContem(jaNotificado,r)) {
                notificacoes.add(r);
            }
        }

        int size = notificacoes.size();
        for(int i = 0; i<size ; i++){
            out.writeBoolean(true);
            notificacoes.get(i).serialize(out);
        }
        out.writeBoolean(false);
    }

    public boolean notificadoContem(List<Recompensa> n , Recompensa recompensa){
        for(Recompensa r : n){
            if(r.getTrotinete().getId()==recompensa.getTrotinete().getId())
                return true;
        }
        return false;
    }
}
