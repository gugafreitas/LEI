package UI;

import java.util.Date;
import java.util.Scanner;

import Controllers.*;
import Devices.*;
import Entidades.*;

public class Menu
{
    public static int menuInicial()
    {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU INICIAL-----------\n\n");
        sb.append("\t(1) - Requisitos base de gestão de entidades\n"); // feito
        sb.append("\t(2) - Efetuar estatisticas sobre o estado do programa\n"); //
        sb.append("\t(3) - Alterar os operadores e os dispositivos de uma casa\n"); // feito
        sb.append("\t(4) - Carregar logs\n"); // feito
        sb.append("\t(5) - Carregar estado\n"); // feito
        sb.append("\t(6) - Salvar estado\n"); // feito
        sb.append("\t(0) - Sair\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuRequisitosBase()
    {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------REQUISITOS BASE-----------\n\n");
        sb.append("\t(1) - Criar SmartBulb\n");
        sb.append("\t(2) - Criar SmartSpeaker\n");
        sb.append("\t(3) - Criar SmartCamera\n");
        sb.append("\t(4) - Criar Casa\n");
        sb.append("\t(5) - Criar Fornecedor\n");
        sb.append("\t(6) - Listar Fornecedores\n");
        sb.append("\t(7) - Listar Casas\n");
        sb.append("\t(8) - Listar Dispositivos\n");
        sb.append("\t(9) - Ligar um dispositivo do programa\n");
        sb.append("\t(10) - Desligar um dispositivo do programa\n");
        sb.append("\t(0) - Sair\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuEstatisticas()
    {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU ESTATISTICAS-----------\n\n");
        sb.append("\t(1) - Casa que mais gastou num determinado período\n");
        sb.append("\t(2) - Comercializador com mais volume de faturação\n");
        sb.append("\t(3) - Lista de faturas emitidas por um comercializador\n");
        sb.append("\t(4) - Ordenação dos maiores consumidores de energia num período de tempo\n");
        sb.append("\t(0) - Sair\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int menuCasa()
    {
        clearWindow();
        StringBuilder sb = new StringBuilder("-----------MENU CASA-----------\n\n");
        sb.append("\t(1) - Mostrar informações da casa\n");
        sb.append("\t(2) - Listar dispositivos por divisão\n");
        sb.append("\t(3) - Adicionar Dispositivo\n");
        sb.append("\t(4) - Ligar todos os dispositivos de uma divisão\n");
        sb.append("\t(5) - Desligar todos os dispositivos de uma divisão\n");
        sb.append("\t(0) - Sair\n");
        sb.append("\tSelecione a opção pretendida: ");
        System.out.println(sb.toString());
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static String pressEnter()
    {
        System.out.println("Pressione qualquer tecla para continuar...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void clearWindow()
    {
        for (int i = 0; i < 5; i++)
        {
            System.out.println();
        }
    }

    // Método que devolve uma smartbulb criada pelo utilizador
    public static SmartDevice getSmartBulb(int id)
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR SMARTBULB---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("\t(0) - COLD\n");
        sb.append("\t(1) - NEUTRAL\n");
        sb.append("\t(2) - WARM\n\n");
        sb.append("Tonalidade: ");

        System.out.println(sb.toString());
        int tonalidade = scanner.nextInt();

        System.out.println("Dimensão: ");
        int dimensao = scanner.nextInt();

        System.out.println("Consumo Diário: ");
        float consumoDiario = scanner.nextInt();

        System.out.println("SmartBulb registada com sucesso!!");

        SmartBulb bulb = new SmartBulb(id, tonalidade, dimensao, consumoDiario);
        System.out.println(bulb.toString());

        return bulb;
    }

    // Método que devolve uma smartbulb criada pelo utilizador
    public static SmartDevice getSmartSpeaker(int id)
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR SMARTSPEAKER---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Volume(0-25): ");

        System.out.println(sb.toString());

        int volume = scanner.nextInt();

        while (volume < 0 || volume > 25)
        {
            System.out.println("Volume inválido! Indique um valor válido(0-25)\n");
            volume = scanner.nextInt();
        }

        System.out.println("Rádio: ");
        String radio = scanner.next();

        System.out.println("Marca: ");
        String marca = scanner.next();

        System.out.println("Consumo Diário: ");
        float consumoDiario = scanner.nextInt();

        System.out.println("SmartSpeaker registada com sucesso!!");
        SmartSpeaker speaker = new SmartSpeaker(id, volume, radio, marca, consumoDiario);

        System.out.println(speaker.toString());
        return speaker;
    }

    // Método que devolve uma smartbulb criada pelo utilizador
    public static SmartDevice getSmartCamera(int id)
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR SMARTCAMERA---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Dimensão: ");
        System.out.println(sb.toString());
        float dimensao = scanner.nextFloat();

        System.out.println("Resolucao: ");
        String resolucao = scanner.next();

        System.out.println("Consumo Diário: ");
        float consumoDiario = scanner.nextFloat();

        System.out.println("SmartCamera registada com sucesso!!");

        SmartCamera camera = new SmartCamera(id, dimensao, resolucao, consumoDiario);
        System.out.println(camera.toString());
        return camera;
    }

    // Método que devolve uma casa criada pelo utilizador
    public static Casa getCasa()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR CASA---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Nome do Proprietário: ");

        System.out.println(sb.toString());
        String nome = scanner.next();

        System.out.println("NIF: ");
        String nif = scanner.next();

        System.out.println("Forncedor: ");
        Fornecedor fornecdor = getFornecedor();

        System.out.println("Casa registada com sucesso!!");
        Casa casa = new Casa(nome, nif, fornecdor);
        System.out.println(casa.toString());
        return casa;
    }

    // Método que devolve um fornecedor criado pelo utilizador
    public static Fornecedor getFornecedor()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------REGISTAR FORNECEDOR---------\n\n");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Empresa: ");

        System.out.println(sb.toString());
        String empresa = scanner.next();

        System.out.println("Valor Base:");
        double valorBase = scanner.nextDouble();

        Fornecedor fornecedor = new Fornecedor(empresa, valorBase);

        System.out.println("Fornecedor registada com sucesso!!");
        System.out.println(fornecedor.toString());

        return fornecedor;
    }

    // Método que devolve uma divisao para ligar/desligar todos os dispositivos da mesma
    public static String getDivisao()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Divisão: ");

        System.out.println(sb.toString());

        return scanner.next();
    }

    // Método que devolve um nif
    public static String getNIF()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("NIF Casa: ");

        System.out.println(sb.toString());
        return scanner.next();
    }

    // Método que devolve um idd
    public static int getID()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("ID Dispositivo: ");

        System.out.println(sb.toString());
        return scanner.nextInt();
    }

    // Método que pede um período de dias ao utilizador
    public static Date getDate()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("");
        sb.append("Introduza os dados a seguir pedidos.\n\n");
        sb.append("Dia: ");

        System.out.println(sb.toString());
        int dia = scanner.nextInt();

        System.out.println("Mês: ");
        int mes = scanner.nextInt();

        System.out.println("Ano: ");
        int ano = scanner.nextInt();

        Date date = new Date(dia, mes, ano);

        return date;
    }

    // Método que devolve o identificador para adicionar um dispositivo à casa
    public static int menuCriarDispositivo()
    {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder("------------ADIOCIONAR DISPOSITIVO---------\n\n");
        sb.append("\t(1):SmartBulb\n");
        sb.append("\t(2):SmartCamera\n");
        sb.append("\t(3):SmartSpeaker\n");

        System.out.println(sb.toString());
        return scanner.nextInt();
    }

    public static void errors(int i)
    {
        StringBuilder sb = new StringBuilder();
        if (i==1) sb.append("****Ficheiro não encontrado***\n");
        else if (i==2) sb.append("****Não foi possivel guardar o Estado****\n");
        else if (i==3) sb.append("****Erro ao ler para as estruturas de dados****\n");
        else if (i==4) sb.append("****Não foi possivel carregar o Estado****\n");
        sb.append("\nPressione enter para continuar...");
        System.out.print(sb.toString());
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearWindow();
    }

}

















