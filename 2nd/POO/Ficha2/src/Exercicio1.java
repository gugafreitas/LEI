import java.util.Scanner;

public class Exercicio1 {
    int[] getArray(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Qual é o tamanho do array? ");
        int tam = sc.nextInt();
        int[] array = new int[tam];

        for(int i=0;i<tam;i++){
            System.out.println("Escreva os valores para o array: ");
            array[i] = sc.nextInt();
        }
        return array;
    }

    int a(int[] array, int tam){
        int min = array[0];

        for(int i=1;i<tam;i++){
            if(array[i]<min) min=array[i];
        }

        return min;
    }

    int[] b(int start, int stop){
        int size = stop - start + 1;
        int[] array = new int[size];

        for(int i=0;i<size;i++){
            array[i] = start+i;
        }

        return array;
    }
}
