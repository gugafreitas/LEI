package src.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import src.Circuitos.*;
import src.UI.UI;
import src.RMFacade;


public class Controller_CriaCircuito {
    public static void run(RMFacade rm) throws Exception{
        ArrayList<String> option = UI.menuCriaCircuitoAdmin();
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer,String> gduCurvas = new HashMap<>(); //associar curva ao gdu 
        Circuito circuito = new Circuito();

        Integer distancia = Integer.parseInt(option.get(1));
        String curvas = option.get(2);
        Integer chicanes = Integer.parseInt(option.get(3));
        Integer retas = Integer.parseInt(option.get(4));

        int nr_curvas = Integer.parseInt(curvas);
        for(int i=1;i<=nr_curvas;i++){
            System.out.println("Insira o grau de dificuldade da curva" + i +": (1-possivel , 2-dificil, 3-impossivel)\n"); //
            String grauDificuldade = scanner.nextLine();
            gduCurvas.put(i,grauDificuldade);
        } 
        // GDU niveis : Dificil, possivel e impossivel 
        // No fim vou ter um map com as curvas e com as dificuldades das mesmas 
        // As retas é facil
        // As chicanes é dificil

        Map<Integer,Elemento> elementos = new HashMap<>();

        for (int i = 0;i<=nr_curvas+chicanes+retas;i++) {
            if (retas != 0 && i%2!=0) {
                circuito.regStraight(1);
            }
            else if (nr_curvas != 0 && i%2==0) {
                Integer grauDificuldade = Integer.parseInt(gduCurvas.get(nr_curvas));
                circuito.regCurva(grauDificuldade);
            }
            else {
                circuito.regChicane(3);
            }
        }

        circuito.setNome(option.get(0));
        circuito.setDistancia(Integer.parseInt(option.get(1)));
        circuito.setVoltas(Integer.parseInt(option.get(5)));
        circuito.setComprimento(elementos);
        circuito.setMapID(0);
        circuito.setNCurvas(nr_curvas);
        circuito.setNChicanes(chicanes);
        circuito.setNRetas(retas);
        rm.circuitos.put(option.get(0), circuito);
    }
}