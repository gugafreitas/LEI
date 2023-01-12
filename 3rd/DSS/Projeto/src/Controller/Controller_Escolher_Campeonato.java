package src.Controller;

import src.RMFacade;
import src.Campeonatos.Campeonato;
import src.UI.UI;
import src.Utilizadores.Utilizador;

public class Controller_Escolher_Campeonato {
    public static void run(RMFacade rm,Utilizador u) throws Exception
    {
        //boolean exit = false;
        System.out.println(rm.campeonatos);//Da print do map de todos os carros com as respetivas infos

        int opcao = UI.menuEscolherCampeonato(); 

        Campeonato c1 = rm.campeonatos.get(opcao);

        u.setCampeonato(c1);

        Controller_Jogar.run(rm,u);
    }
}
