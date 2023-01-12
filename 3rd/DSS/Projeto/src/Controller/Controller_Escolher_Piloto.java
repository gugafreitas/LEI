package src.Controller;

import src.Pilotos.Piloto;
import src.Utilizadores.Utilizador;
import src.RMFacade;
import src.UI.UI;

public class Controller_Escolher_Piloto {
    public static void run(RMFacade rm,Utilizador u) throws Exception
    {
        //boolean exit = false;
        System.out.println(rm.pilotos);//Da print do map de todos os carros com as respetivas infos

        int opcao = UI.menuEscolherPiloto(); 

        Piloto p1 = rm.pilotos.get(opcao);

        u.setPiloto(p1);

        Controller_Jogar.run(rm,u);
    }
}
