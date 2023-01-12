package UI;

import java.io.Serializable;
import java.util.*;

public class Menu implements Serializable{
    public interface Handler {
         void execute();
    }

    private final Scanner scan;
    private String titulo;
    private final List<String> opcoes;
    private final List<Handler> handlers;

    public Menu(String titulo, List<String> opcoes) {
        this.titulo = titulo;
        this.opcoes = new ArrayList<>(opcoes);
        this.handlers = new ArrayList<>();
        this.opcoes.forEach(s-> this.handlers.add(()->System.out.println("\nOpção não implementada!")));
        this.scan = new Scanner(System.in);
    }

    public Menu(List<String> opcoes) { this("Menu", opcoes); }

    public Menu(String[] opcoes) {
         this(Arrays.asList(opcoes));
     }

    public void run() {
        int op;
        do {
            show();
            op = readOption();
            if (op>0) {
                System.out.println("\033[H\033[2J");
                this.handlers.get(op-1).execute();
            }
        } while (op != 0);
        System.out.println("\033[H\033[2J");
    }

    public void setHandler(int i, Handler h) {
         this.handlers.set(i-1, h);
     }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    private void show() {
        System.out.println("\033[H\033[2J");
        System.out.println("");
        System.out.println("\u001B[33m"+this.titulo+"\u001B[0m");
        for (int i = 0; i < this.titulo.length(); i++) {
            System.out.print("─");
        }
        System.out.println("");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print("\u001B[31m");
            System.out.print(i+1);
            System.out.print(". ");
            System.out.print("\u001B[0m");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("\u001B[31m0. \u001B[0mSair");
    }

    public static void show2(String titulo, List<String> opcoes) {
        System.out.println("\033[H\033[2J");
        System.out.println("");
        System.out.println("\u001B[33m"+titulo+"\u001B[0m");
        for (int i = 0; i < titulo.length(); i++) {
            System.out.print("─");
        }
        System.out.println("");
        for (int i=0; i<opcoes.size(); i++) {
            System.out.print("\u001B[33m");
            System.out.print("\u001B[0m");
            System.out.println(opcoes.get(i));
        }
        System.out.print("\u001B[36m");
        System.out.print("\u001B[0m");
        System.out.println("\nPrima enter para continuar");
    }

    public static void show3(String titulo, List<String> opcoes) {
        System.out.println("\033[H\033[2J");
        System.out.println("");
        System.out.println("\u001B[33m"+titulo+"\u001B[0m");
        for (int i = 0; i < titulo.length(); i++) {
            System.out.print("─");
        }
        System.out.println("");
        for (int i=0; i<opcoes.size(); i++) {
            System.out.print("\u001B[31m");
            System.out.print(i+1);
            System.out.print(". ");
            System.out.print("\u001B[0m");
            System.out.println(opcoes.get(i));
        }
        System.out.println("\u001B[31m0. \u001B[0mSair");
    }

    public static void show4(String mensagem) {
        System.out.println("\033[H\033[2J");
        System.out.print(mensagem);
    }

    public static void showErrorMessage(String message){
        Menu.show4("\u001B[31m[Erro]\u001B[0m " + message);
    }

    private int readOption() {
         int op;

         System.out.print("\u001B[31mOpção: \u001B[0m");
         try {
             String line = scan.nextLine();
             op = Integer.parseInt(line);
         }
         catch (NumberFormatException e) {  //Não foi inscrito um int
             op = -1;
         }
         if (op<0 || op>this.opcoes.size()) {
             System.out.println("\033[H\033[2J");
             System.out.println("Opção Inválida");
             op = -1;
         }
         return op;
     }
}