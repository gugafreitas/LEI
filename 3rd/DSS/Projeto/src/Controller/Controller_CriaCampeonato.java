package src.Controller;

import java.util.ArrayList;
import java.util.Scanner;

import src.RMFacade;
import src.Campeonatos.Campeonato;
import src.Circuitos.Circuito;
import src.UI.UI;
// import src.Utilizadores.Utilizador;
import java.util.Arrays;
import java.util.HashMap;


public class Controller_CriaCampeonato {
    public static void run(RMFacade rm) throws Exception{
        String option_1 = UI.menuCriaCampeonato();


        if(rm.checkNameCircuito(option_1)) {
            System.out.println("Agora indique os circuitos que pretende adicionar ao seu campeonato(neste formato : x y z ...)");
            System.out.println(rm.circuitos);
            Scanner scanner = new Scanner(System.in);
			String [] strsplit = scanner.nextLine().split("");

			ArrayList<String> circuitos = new ArrayList<String>(Arrays.asList(strsplit));
      
     
			HashMap<String, Circuito> circuitos_final = new HashMap<>();
				
			for (String str : circuitos) {
     
				circuitos_final.put(str, rm.circuitos.get(str));
			}

			Campeonato c = new Campeonato(option_1, circuitos_final);
				
			rm.campeonatos.put(option_1,c);
            scanner.close();
        }
        else System.out.println("Este nome ja esta ocupado");
    }      
}

