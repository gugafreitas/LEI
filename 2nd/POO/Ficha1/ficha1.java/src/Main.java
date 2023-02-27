import java.util.Scanner;

public class Main {
    static double celsiusParaFarenheit(double graus){
        double far = (1.8*graus) + 32;

        return far;
    }
    static int maximoNumeros(int a, int b){
        int max=0;

        if (a>b) max=a;
        else max=b;

        return max;
    }
    static void criaDescricaoConta(String nome, double saldo){
        System.out.println(nome + " custa: " + saldo);
    }
    static double eurosParaLibras(double valor, double taxaConversao){
        double libras = valor * taxaConversao;

        return libras;
    }

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        System.out.println("CONVERSÃO DE CELSIUS PARA FARENHEIT\n");
        System.out.println("Indique quantos graus estão: ");
        double graus = ler.nextDouble();
        double far = Main.celsiusParaFarenheit(graus);
        System.out.println("O valor em Farenheit é: " + far + "\n");

        System.out.println("MÁXIMO DE 2 NÚMEROS\n");
        System.out.println("Indique o primeiro número: ");
        int fst = ler.nextInt();
        System.out.println("Indique o segundo número: ");
        int snd = ler.nextInt();
        int max = maximoNumeros(fst,snd);
        System.out.println("O maior dos dois números é: " + max + "\n");

        /*System.out.println("CRIA DESCRIÇÃO DA CONTA\n");
        System.out.println("Indique o nome do produto: ");
        String prod = ler.nextLine();
        System.out.println("Indique o valor do produto: ");
        double saldo = ler.nextDouble();
        System.out.println(criaDescricaoConta(prod,saldo));*/

        
    }
}