package src.Controller;

import java.util.ArrayList;
//import java.util.Scanner;

import src.Pilotos.*;
import src.UI.UI;
import src.RMFacade;


public class Controller_CriaPiloto {
    public static void run(RMFacade rm) throws Exception{
        ArrayList<String> option = UI.menuCriaPilotoAdmin();

        if (rm.pilotos.containsKey(option.get(0))==false) {
        Piloto piloto = new Piloto(option.get(0),false,Float.parseFloat(option.get(1)),Float.parseFloat(option.get(2)));
        rm.pilotos.put(option.get(0), piloto);
        }

        else System.out.println("O nome do piloto não se encontra disponivel");
    }
}