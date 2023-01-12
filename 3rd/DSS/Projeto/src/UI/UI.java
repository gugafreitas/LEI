package src.UI;

import src.Utilizadores.Utilizador;

import java.util.ArrayList;
//import java.util.Date;
import java.util.Scanner;
//import src.RMFacade;

public class UI 
{
    public static int menuInicial()
    {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("\tBem vindo ao F1Manager, por favor efetue o login ou o registo.\n");
        sb.append("\t(1) - Registo\n"); 
        sb.append("\t(2) - Login\n"); 
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);

        return sc.nextInt();
    }

    /*private void menuLogin(){
        Scanner leitor = new Scanner(System.in);
        Utilizador util = new Utilizador();

        System.out.print("ID do cliente: ");
        util.setNome_utilizador(leitor.nextLine());

        System.out.print("Nome do cliente: ");
        leitor.nextLine();// limpa o scanner
        util.setPassword(leitor.nextLine());

    }*/

    public static ArrayList<String> menuLogin()
    {
        Scanner scanner = new Scanner(System.in);
        ArrayList res = new ArrayList<>();

        StringBuilder sb = new StringBuilder("------------MENU LOGIN---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome Utilizador: ");
        res.add(scanner.nextLine());

        System.out.println("Password: ");
        res.add(scanner.nextLine());

        System.out.println("Login efetuado com sucesso!!");

        System.out.println(res);

        return res;
    }

    public static ArrayList<String> menuRegisto()
    {
        clearWindow();
        ArrayList res = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("-----------MENU REGISTO-----------\n\n");
        System.out.println("\tNome Utilizador\n"); 
        res.add(sc.nextLine());
        System.out.println("\tPassword\n"); 
        res.add(sc.nextLine());
        return res;
    }


    public static int menuPrincipal_normal() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("\tSelecione a opção pretendida com base na sua preferência.\n"); 
        sb.append("\t(1) - Jogar\n");
        sb.append("\t(2) - Sair\n"); 
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menu_Jogar() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU JOGAR-----------\n\n");
        sb.append("\tSelecione a opção pretendida com base na sua preferência.\n"); 
        sb.append("\t(1) - Escolher carro\n");
        sb.append("\t(2) - Escolher campeonato\n");
        sb.append("\t(3) - Escolher piloto\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuEscolherCarro() {
        clearWindow();
        //Lista dos carros
        System.out.println("\tSelecione a opção pretendida: ");
        Scanner sc = new Scanner(System.in);

        return sc.nextInt();
    }

    public static int menuEscolherCampeonato() {
        clearWindow();
        //Lista dos campeonatos
        System.out.println("\tSelecione a opção pretendida: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuEscolherPiloto() {
        clearWindow();
        //Lista dos pilotos
        System.out.println("\tSelecione a opção pretendida: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuPrincipal_admin() {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("\tSelecione a opção pretendida com base na sua preferência.\n"); 
        sb.append("\t(1) - Criar Carro\n"); 
        sb.append("\t(2) - Criar Piloto\n");
        sb.append("\t(3) - Criar Circuito\n");
        sb.append("\t(4) - Criar Campeonato\n"); 
        sb.append("\t(5) - Jogar\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static ArrayList<String> menuCriaCarroAdmin() {
        clearWindow();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> criaCarro_termos = new ArrayList<>();

        System.out.println("-----------MENU CRIA CARRO-----------\n\n");
        System.out.println("Introduza os dados a seguir pedidos.\n\n");

        System.out.println("Insira a classe do carro:\n(1) - C1\n(2) - C2\n(3) - GT\n(4) - SC\n");
        String classe = scanner.nextLine();
        criaCarro_termos.add(classe);

        System.out.println("Insira a marca do carro:\n");
        String marca = scanner.nextLine();
        criaCarro_termos.add(marca);

        System.out.println("Insira o modelo do carro:\n");
        String modelo = scanner.nextLine();
        criaCarro_termos.add(modelo);

        System.out.println("Insira o PAC:\n");
        String pac = scanner.nextLine();
        criaCarro_termos.add(pac);

        System.out.println("Insira a fiabilidade:\n");
        String fiabilidade = scanner.nextLine();
        criaCarro_termos.add(fiabilidade);

        System.out.println("Insira a cilindrada:\n");
        String cilindrada = scanner.nextLine();
        criaCarro_termos.add(cilindrada);

        System.out.println("Insira a potência:\n");
        String potencia = scanner.nextLine();
        criaCarro_termos.add(potencia);

        System.out.println("Insira o tipo de pneus:\n");
        String pneus = scanner.nextLine();
        criaCarro_termos.add(pneus);

        System.out.println("O carro é híbrido?:\n");
        String hibrido = scanner.nextLine();
        criaCarro_termos.add(hibrido);
        
        return criaCarro_termos;
    }

    public static ArrayList<String> menuCriaPilotoAdmin() {
        clearWindow();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> criaPiloto_termos = new ArrayList<>();

        System.out.println("-----------MENU CRIA PILOTO-----------\n\n");
        System.out.println("Introduza os dados a seguir pedidos.\n\n");

        System.out.println("Insira o nome do piloto:\n");
        String nome = scanner.nextLine();
        criaPiloto_termos.add(nome);

        System.out.println("Introduza os níveis de perícia a seguir pedidos.\n\n");
        
        System.out.println("Insira o nível de CTS do piloto:\n");
        String cts = scanner.nextLine();
        criaPiloto_termos.add(cts);

        System.out.println("Insira o nível de SVA do piloto:\n");
        String sva = scanner.nextLine();
        criaPiloto_termos.add(sva);
        
        return criaPiloto_termos;
    }

    public static ArrayList<String> menuCriaCircuitoAdmin() {
        clearWindow();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> criaCircuito_termos = new ArrayList<>();

        System.out.println("-----------MENU CRIA CIRCUITO-----------\n\n");
        System.out.println("Introduza os dados a seguir pedidos.\n\n");

        System.out.println("Insira o nome do circuito:\n");
        String nome = scanner.nextLine();
        criaCircuito_termos.add(nome);
        
        System.out.println("Insira a distância do circuito:\n");
        String distancia = scanner.nextLine();
        criaCircuito_termos.add(distancia);

        System.out.println("Insira o número de curvas do circuito:\n");
        String curvas = scanner.nextLine();
        criaCircuito_termos.add(curvas);

        System.out.println("Insira o número de chicanes do circuito:\n");
        String chicanes = scanner.nextLine();
        criaCircuito_termos.add(chicanes);
        
        Integer retas = Integer.parseInt(distancia) - Integer.parseInt(chicanes)*100 - Integer.parseInt(curvas)*100;

        System.out.println("O circuito tem: " + retas + "retas\n");
        criaCircuito_termos.add(retas.toString());

        System.out.println("Insira o número de voltas do circuito:\n");
        String voltas = scanner.nextLine();
        criaCircuito_termos.add(voltas);

        return criaCircuito_termos;
    }

    public static String menuCriaCampeonato() {
        clearWindow();
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------MENU CRIA CAMPEONATO-----------\n\n");
        System.out.println("Introduza os dados a seguir pedidos.\n\n");

        System.out.println("Indique o nome do seu campeonato");

        String r = scanner.nextLine();
        return r;
    }

    public static String pressEnter()
    {
        System.out.println("Pressione qualquer tecla para continuar...");
        Scanner scanner = new Scanner(System.in);
        String r = scanner.nextLine();

        return r;
    }

    public static void clearWindow()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println();
        }
    }



    public static void main(String [] a) {
        menuInicial();
    }

}